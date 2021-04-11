package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implementation of a simple HTTP server. Server is configured by a special
 * {@code server.properties} file which is expected as a command line argument.
 * This server is capable of remembering currently active sessions. It also
 * uses multithreading for processing each clients requests in a separate thread.
 * 
 * @author Matija Frandolić
 */
public class SmartHttpServer {

	/**
	 * Address on which the server listens.
	 */
	private String address;
	
	/**
	 * Domain name of the server.
	 */
	private String domainName;
	
	/**
	 * Port on which the server listens.
	 */
	private int port;
	
	/**
	 * Number of threads for thread pool.
	 */
	private int workerThreads;
	
	/**
	 * Duration of session (in seconds).
	 */
	private int sessionTimeout;
	
	/**
	 * Map of known mime types.
	 */
	private Map<String,String> mimeTypes = new HashMap<>();
	
	/**
	 * Thread on which the server is running.
	 */
	private ServerThread serverThread;
	
	/**
	 * Thread pool with for client worker threads.
	 */
	private ExecutorService threadPool;
	
	/**
	 * Path to root directory from which the files are served.
	 */
	private Path documentRoot;
	
	/**
	 * Map of known workers.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of remembered sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	
	/**
	 * Object used to generate session ID for new sessions.
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Constructs a new {@code SmartHttpServer} and configures it according to the
	 * configuration file whose path is passed as an argument.
	 * 
	 * @param  configFileName path to the configuration file
	 */
	public SmartHttpServer(String configFileName) {
		Properties serverProperties = new Properties();
		
		try (InputStream is = Files.newInputStream(Paths.get(configFileName))) {
			serverProperties.load(is);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		address = serverProperties.getProperty("server.address");
		domainName = serverProperties.getProperty("server.domainName");
		port = Integer.parseInt(serverProperties.getProperty("server.port"));
		workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
		documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));

		Properties mimeProperties = new Properties();
		Properties workersProperties = new Properties();
		Path mimePropertiesPath = Paths.get(serverProperties.getProperty("server.mimeConfig"));
		Path workersPropertiesPath = Paths.get(serverProperties.getProperty("server.workers"));
		
		try (InputStream mimeInputStream = Files.newInputStream(mimePropertiesPath);
			 InputStream workersInputStream = Files.newInputStream(workersPropertiesPath)) {
			
			mimeProperties.load(mimeInputStream);
			workersProperties.load(workersInputStream);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		for (String key : mimeProperties.stringPropertyNames()) {
			mimeTypes.put(key, mimeProperties.getProperty(key));
		}
		
		for (String key : workersProperties.stringPropertyNames()) {
			String fqcn = workersProperties.getProperty(key);
			IWebWorker iww = getWorkerInstance(fqcn);
			workersMap.put(key, iww);
		}
		
		Thread sessionCleanup = new Thread(() -> {
			while (true) {
				sessions.values().removeIf(
					entry -> entry.validUntil < System.currentTimeMillis()
				);
				try {
					Thread.sleep(5 * 60 * 1000);
				} catch (InterruptedException e) {
					// ignore
				}
			}
		});
		sessionCleanup.setDaemon(true);
		sessionCleanup.start();
	}
	
	/**
	 * Starts the server if it is not currently running.
	 */
	protected synchronized void start() {
		if (serverThread == null || !serverThread.isAlive()) {
			serverThread = new ServerThread();
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
		}
	}
	
	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	/**
	 * Implementation of {@link Thread} that represents thread on which the
	 * server is running.
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(
						InetAddress.getByName(address), port)
				);
				while(!isInterrupted()) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
	
	/**
	 * Returns an instance of {@link IWebWorker} that is created according to its
	 * fully qualified class name.
	 * 
	 * @param  fqcn fully qualified class name of the wanted worker
	 * @return      an instance of {@code IWebWorker}
	 * @throws RuntimeException if it is not possible to complete this operation
	 */
	private IWebWorker getWorkerInstance(String fqcn) {
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
			return (IWebWorker) newObject;			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Model of a session.
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session ID.
		 */
		String sid;
		
		/**
		 * Address or domain name of the host. 
		 */
		String host;
		
		/**
		 * Time until this session is valid.
		 */
		long validUntil;
		
		/**
		 * Map of permanent parameters for this session.
		 */
		Map <String, String> map;
		
	}
	
	/**
	 * Implementation of {@link Runnable} and {@link IDispatcher} that represents
	 * object which can process clients request and provide appropriate response.
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * Client socket.
		 */
		private Socket csocket;
		
		/**
		 * Input stream of the client socket.
		 */
		private InputStream istream;
		
		/**
		 * Output stream of the client socket.
		 */
		private OutputStream ostream;
		
		/**
		 * HTTP version.
		 */
		private String version;
		
		/**
		 * HTTP method.
		 */
		private String method;
		
		/**
		 * Address or domain name of the host. 
		 */
		private String host;
		
		/**
		 * Map of paramters.
		 */
		private Map<String,String> params = new HashMap<>();
		
		/**
		 * Map of temporary paramters.
		 */
		private Map<String,String> tempParams = new HashMap<>();
		
		/**
		 * Map of persistent paramters.
		 */
		private Map<String,String> permPrams = new HashMap<>();
		
		/**
		 * List of cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<>();
		
		/**
		 * Session ID.
		 */
		private String SID;
		
		/**
		 * Request context to which the response is written.
		 */
		private RequestContext context;
	
		/**
		 * Constructs a new {@code ClientWorker} from the given client socket.
		 * 
		 * @param csocket client socket
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}
		
		@Override
		public void run() {
			List<String> request;
			try {
				istream = new BufferedInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				request = readRequest();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}

			if (request.isEmpty()) {
				sendError(400, "Bad Request");
				return;
			}
			
			String[] firstLine = request.get(0).split(" ");
			if (firstLine.length != 3) {
				sendError(400, "Bad Request");
				return;
			}
			
			method = firstLine[0].toUpperCase();
			version = firstLine[2].toUpperCase();
			if (!method.equals("GET") || 
				!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
				sendError(400, "Bad Request");
				return;
			}
			
			host = domainName;
			for (String header : request) {
				if (header.startsWith("Host:")) {
					host = header.split(":")[1].trim();
				}
			}
			
			synchronized(SmartHttpServer.this) {
				checkSession(request);
			}
			
			String requestedPath = firstLine[1];
			String path = requestedPath;
			String paramString = null;
			int indexOfQuestionMark = requestedPath.indexOf("?");
			if (indexOfQuestionMark > 0) {
				path = requestedPath.substring(0, indexOfQuestionMark);
				paramString = requestedPath.substring(indexOfQuestionMark + 1);
			}
			
			parseParameters(paramString);
			
			try {
				internalDispatchRequest(path, true);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Processes the request for the resource with the given URL.
		 * 
		 * @param  urlPath    URL of the requested resource
		 * @param  directCall if {@code true}, it represents that this resource 
		 *                    was requested directly by the client
		 * @throws Exception  if any exception occurs
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (urlPath.startsWith("/ext/")) {
				String className = urlPath.replace("/ext/", "");
				String fqcn = "hr.fer.zemris.java.webserver.workers." + className;
				try {
					IWebWorker iww = getWorkerInstance(fqcn);
					createContext();
					iww.processRequest(context);
					closeResources();
				} catch (RuntimeException e) {
					sendError(404, "Not Found");
				}
				return;
			}
			
			IWebWorker worker = workersMap.get(urlPath);
			if (worker != null) {
				createContext();
				worker.processRequest(context);
				closeResources();
				return;
			}
			
			if (urlPath.startsWith("/private") && directCall) {
				sendError(404, "Not Found");
				return;
			}
			
			if (urlPath.equals("/")) {
				sendError(403, "Forbidden");
				return;
			}
			
			Path resolvedPath = documentRoot.resolve(urlPath.substring(1));
			
			if (!Files.isRegularFile(resolvedPath) || !Files.isReadable(resolvedPath)) {
				sendError(404, "Not Found");
				return;
			}

			String extension = "";
			int indexOfDot = resolvedPath.getFileName().toString().lastIndexOf("."); 
			if (indexOfDot > 0) {
				extension = resolvedPath.getFileName().toString().substring(indexOfDot + 1);
			}
			
			if (extension.equals("smscr")) {
				executeScript(resolvedPath);
				closeResources();
				return;
			}
			
			String mimeType = mimeTypes.get(extension);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			createContext();
			context.setMimeType(mimeType);
			
			InputStream is = Files.newInputStream(resolvedPath);
			long length = Files.size(resolvedPath);
			context.setContentLength(length);
			
			byte[] buf = new byte[1024];
			while (true) {
				int r = is.read(buf);
				if (r < 1) {
					break;
				}
				context.write(buf, 0, r);
			}
			
			closeResources();
		}
		
		/**
		 * Creates request context if it is not already created.
		 */
		private void createContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, 
					outputCookies, tempParams, this, SID
				);
			}
		}
		
		/**
		 * Flushes output stream and closes output stream, input stream and
		 * client socket.
		 * 
		 * @throws IOException if I/O error occurs
		 */
		private void closeResources() throws IOException {
			ostream.flush();
			ostream.close();
			istream.close();
			csocket.close();
		}
		
		/**
		 * Sends empty HTTP response with the given status code and status text.
		 * This method closes resources held by this {@code ClientWorker}.
		 * 
		 * @param statusCode status code of the response
		 * @param statusText status text of the response
		 */
		private void sendError(int statusCode, String statusText) {
			createContext();
			context.setStatusCode(statusCode);
			context.setStatusText(statusText);
			try {
				context.write(statusCode + " " + statusText);
				closeResources();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		
		/**
		 * Parses the parameters from the given string.
		 * 
		 * @param paramString string from which to parse the parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			for (String param : paramString.split("&")) {
				String key = param;
				String value = "";
				int indexOfEqualsSign = param.indexOf("=");
				if (indexOfEqualsSign > 0) {
					key = param.substring(0, indexOfEqualsSign);
					value = param.substring(indexOfEqualsSign + 1);
				}
				params.put(key, value);
			}
		}
		
		/**
		 * Reads the HTTP request and returns list of strings that represent
		 * lines of the request.
		 *  
		 * @return list of strings that represent lines of the request
		 * @throws IOException if I/O error occurs
		 */
		private List<String> readRequest() throws IOException {
			byte[] requestBytes = extractRequestBytes();
			
			if (requestBytes != null) {
				String requestString = new String(requestBytes, StandardCharsets.ISO_8859_1);
				return extractRequestHeader(requestString);
			} else {
				return new ArrayList<>();
			}
		}
		
		/**
		 * Returns the byte array that contains data from the HTTP request.
		 * 
		 * @return the byte array that contains data from the HTTP request
		 * @throws IOException if I/O error occurs
		 */
		private byte[] extractRequestBytes() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			
		l:	while (true) {
				int b = istream.read();
				
				if (b == -1) {
					return null;
				}
				if (b != 13) {
					bos.write(b);
				}
				
				switch(state) {
				case 0: 
					if (b==13) { state=1; } else if (b == 10) state = 4;
					break;
				case 1: 
					if (b==10) { state=2; } else state = 0;
					break;
				case 2: 
					if (b==13) { state=3; } else state = 0;
					break;
				case 3: 
					if (b==10) { break l; } else state = 0;
					break;
				case 4: 
					if (b==10) { break l; } else state = 0;
					break;
				}
			}
			
			return bos.toByteArray();
		}
		
		/**
		 * Reads the HTTP request that is contained in the given string and returns 
		 * list of strings that represent lines of the request.
		 * 
		 * @param  requestHeader string that represents whole HTTP request
		 * @return               list of strings that represent lines of the request
		 */
		private List<String> extractRequestHeader(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty()) {
					break;
				}
				char c = s.charAt(0);
				if(c == ' ' || c == '\t') {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		/**
		 * Executes the script based on the given URL of the script.
		 * 
		 * @param  scriptPath  URL of the script to be executed
		 * @throws IOException if I/O error occurs
		 */
		private void executeScript(Path scriptPath) throws IOException {
			String documentBody = new String(
				Files.readAllBytes(scriptPath), 
				StandardCharsets.UTF_8
			);
			createContext();
			new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), 
				context
			).execute();
		}
		
		/**
		 * Checks whether the session already exists for the client, based on the
		 * received HTTP request.
		 * 
		 * @param request list of strings that represent lines of the request
		 */
		private void checkSession(List<String> request) {
			boolean foundCookie = false;
			
			for (String headerLine : request) {
				if (!headerLine.startsWith("Cookie:")) {
					continue;
				}
				
				foundCookie = true;
				headerLine = headerLine.replace("Cookie:", "");
				String[] cookieParams = headerLine.split(";");
				String sidCandidate = null;
				
				for (String param : cookieParams) {
					String key = param;
					String value = "";
					int indexOfEqualsSign = param.indexOf("=");
					if (indexOfEqualsSign > 0) {
						key = param.substring(0, indexOfEqualsSign).trim();
						value = param.substring(indexOfEqualsSign + 1);
					}
					if (key.equals("sid") && value.startsWith("\"") && value.endsWith("\"")) {
						sidCandidate = value.substring(1, value.length() - 1);
					}
				}
				
				if (sidCandidate == null) {
					createNewSession();
				} else {
					SessionMapEntry possibleSession = sessions.get(sidCandidate);
					
					if (possibleSession == null || !host.equals(possibleSession.host)) {
						createNewSession();
					} else if (possibleSession.validUntil < System.currentTimeMillis()) {
						sessions.remove(sidCandidate);
						createNewSession();						
					} else {
						possibleSession.validUntil = System.currentTimeMillis() + 
						                             sessionTimeout * 1000;
						SID = possibleSession.sid;
					}
				}
				
				permPrams = sessions.get(SID).map;
			}
			
			if (!foundCookie) {
				createNewSession();
				permPrams = sessions.get(SID).map;
			}
		}
		
		/**
		 * Creates new session if one does not already exist.
		 */
		private void createNewSession() {
			SID = generateRandomSid();
			SessionMapEntry entry = new SessionMapEntry();
			entry.sid = SID;
			entry.host = host;
			entry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
			entry.map = new ConcurrentHashMap<String, String>();
			outputCookies.add(new RCCookie("sid", SID, null, host, "/", true));
			sessions.put(SID, entry);
		}
		
		/**
		 * Generates random session ID that is a string that consists of 20 upper 
		 * case letters.
		 * 
		 * @return session ID that was randomly generated
		 */
		private String generateRandomSid() {
			StringBuilder sb = new StringBuilder();
			final int length = 20;
			
			for (int i = 0; i < length; i++) {
				char randomUpper = (char) (sessionRandom.nextInt('Z' - 'A' + 1) + 'A');
				sb.append(randomUpper);
			}
			
			return sb.toString();
		}
		
	}
	
	/**
	 * Main method of the program that is responsible for starting the server.
	 * 
	 * @param args command-line arguments (path to server.properties configuration file)
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected path to server.properties configuration file.");
			System.exit(1);
		}
		
		SmartHttpServer server = null;
		try {
			server = new SmartHttpServer(args[0]);			
		} catch (Exception e) {
			System.out.println("Wrong or invalid configuration file.");
			System.exit(1);
		}
		
		server.start();
	}
	
}
