package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Model of HTTP response. This class provides methods for setting header properties
 * and writing content to HTTP response.
 * 
 * @author Matija Frandolić
 */
public class RequestContext {
	
	/**
	 * Output stream to which the response is written.
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset that is used for writing content of the response.
	 */
	private Charset charset;
	
	/**
	 * Encoding property of HTTP header.
	 */
	private String encoding = "UTF-8";
	
	/**
	 * Status code of HTTP response.
	 */
	private int statusCode = 200;
	
	/**
	 * Status text of HTTP response.
	 */
	private String statusText = "OK";
	
	/**
	 * Content-Type property of HTTP header.
	 */
	private String mimeType = "text/html";
	
	/**
	 * Content-Length property of HTTP header.
	 */
	private Long contentLength = null;
	
	/**
	 * Map of parameters.
	 */
	private Map<String, String> parameters;
	
	/**
	 * Map of temporary parameters.
	 */
	private Map<String, String> temporaryParameters;
	
	/**
	 * Map of persistent parameters.
	 */
	private Map<String, String> persistentParameters;
	
	/**
	 * List of cookies.
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Flag that indicates whether HTTP header was already generated.
	 */
	private boolean headerGenerated = false;
	
	/**
	 * Dispatcher responsible for dispatching received requests.
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Session ID.
	 */
	private String sid;
	
	/**
	 * Constructs a new {@code RequestContext} from the given arguments. If
	 * either of parameter maps or cookies list is {@code null}, they are treated
	 * as empty.
	 * 
	 * @param outputStream         output stream to which the response is written
	 * @param parameters           map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies        list of cookies
	 * @param temporaryParameters  map of temporary parameters
	 * @param dispatcher           dispatcher responsible for dispatching received requests
	 * @param sid                  session ID
	 * @throws NullPointerException if the given output stream is {@code null}
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String, String> parameters,
			Map<String, String> persistentParameters, 
			List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters,
			IDispatcher dispatcher,
			String sid) {
		
		this.outputStream = Objects.requireNonNull(outputStream, 
				"Output stream must not be null.");
		
		this.parameters = parameters;
		if (parameters == null) {
			this.parameters = new HashMap<String, String>();
		}
		
		this.persistentParameters = persistentParameters;
		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<String, String>();
		}
		
		this.outputCookies = outputCookies;
		if (outputCookies == null) {
			this.outputCookies = new ArrayList<RCCookie>();
		}
		
		this.temporaryParameters = temporaryParameters;
		if (temporaryParameters == null) {
			this.temporaryParameters = new HashMap<String, String>();
		}
		
		this.dispatcher = dispatcher;
		this.sid = sid;
	}

	/**
	 * Constructs a new {@code RequestContext} from the given arguments. If
	 * either of parameter maps or cookies list is {@code null}, they are treated
	 * as empty.
	 * 
	 * @param outputStream         output stream to which the response is written
	 * @param parameters           map of parameters
	 * @param persistentParameters map of persistent parameters
	 * @param outputCookies        list of cookies
	 * @throws NullPointerException if the given output stream is {@code null}
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String, String> parameters,
			Map<String, String> persistentParameters, 
			List<RCCookie> outputCookies) {
		
		this(outputStream, parameters, persistentParameters, outputCookies, null, null, null);
	}
	
	/**
	 * Sets the encoding property of HTTP response.
	 * 
	 * @param  encoding encoding property of HTTP response
	 * @throws RuntimeException if this property is set after the HTTP header has
	 *                          already been generated 
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = encoding;
	}

	/**
	 * Sets the status code property of HTTP response.
	 * 
	 * @param  statusCode status code property of HTTP response
	 * @throws RuntimeException if this property is set after the HTTP header has
	 *                          already been generated 
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text property of HTTP response.
	 * 
	 * @param  statusText status text property of HTTP response
	 * @throws RuntimeException if this property is set after the HTTP header has
	 *                          already been generated 
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = statusText;
	}

	/**
	 * Sets the Content-Type property of HTTP response.
	 * 
	 * @param  mimeType Content-Type property of HTTP response
	 * @throws RuntimeException if this property is set after the HTTP header has
	 *                          already been generated 
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = mimeType;
	}

	/**
	 * Sets the Content-Length property of HTTP response.
	 * 
	 * @param  contentLength Content-Length property of HTTP response
	 * @throws RuntimeException if this property is set after the HTTP header has
	 *                          already been generated 
	 */
	public void setContentLength(Long contentLength) {
		checkHeaderGenerated();
		this.contentLength = contentLength;
	}
	
	/**
	 * Adds the given cookie to the header of HTTP response.
	 * 
	 * @param  rcCookie cookie to be added to the header of HTTP response
	 * @throws RuntimeException if this cookie is added after the HTTP header has
	 *                          already been generated 
	 */
	public void addRCCookie(RCCookie rcCookie) {
		checkHeaderGenerated();
		outputCookies.add(rcCookie);
	}
	
	/**
	 * Returns the value of the given parameter.
	 * 
	 * @param  name parameter for which to return the value
	 * @return      value of the given parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Returns an unmodifiable set of names of all the parameters.
	 * 
	 * @return an unmodifiable set of names of all the parameters
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	
	/**
	 * Returns the value of the given persistent parameter.
	 * 
	 * @param  name persistent parameter for which to return the value
	 * @return      value of the given persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Returns an unmodifiable set of names of all the persistent parameters.
	 * 
	 * @return an unmodifiable set of names of all the persistent parameters
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Sets the value of the given persistent parameter to the given value.
	 * 
	 * @param name  persistent parameter for which to set the value
	 * @param value new value of the persistent parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes the given persistent parameter.
	 * 
	 * @param name persistent parameter to be removed
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Returns the value of the given temporary parameter.
	 * 
	 * @param  name temporary parameter for which to return the value
	 * @return      value of the given temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Returns an unmodifiable set of names of all the temporary parameters.
	 * 
	 * @return an unmodifiable set of names of all the temporary parameters
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Sets the value of the given temporary parameter to the given value.
	 * 
	 * @param name  temporary parameter for which to set the value
	 * @param value new value of the temporary parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the given temporary parameter.
	 * 
	 * @param name temporary parameter to be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Returns the session ID.
	 * 
	 * @return the session ID
	 */
	public String getSessionID() {
		return sid;
	}
	
	/**
	 * Returns the {@link IDispatcher} object that is responsible for dispatching 
	 * received requests.
	 * 
	 * @return {@code IDispatcher} object that is responsible for dispatching 
	 *         received requests
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Writes {@code data.length} bytes from the specified byte array as content of the
	 * HTTP response. Header is generated automatically at first call of any of
	 * {@code write} methods.
	 * 
	 * @param  data data that is written
	 * @return      reference to this object
	 * @throws IOException if I/O error occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes {@code len} bytes from the specified byte array starting at offset 
	 * {@code offset} as content of the HTTP response. Header is generated automatically 
	 * at first call of any of {@code write} methods.
	 * 
	 * @param  data   data that is written
	 * @param  offset start offset in the data
	 * @param  len    number of bytes to write
	 * @return        reference to this object
	 * @throws IOException if I/O error occurs
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		writeHeader();
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Writes the given string as content of the HTTP response. Encoding 
	 * {@code UTF-8} is used by default unless it has been set explicitly by 
	 * {@link RequestContext#setEncoding(String)} method. Header is generated 
	 * automatically at first call of any of {@code write} methods.
	 * 
	 * @param  text string that is written
	 * @return      reference to this object
	 * @throws IOException if I/O error occurs
	 */
	public RequestContext write(String text) throws IOException {
		writeHeader();
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Checks if HTTP header has already been generated by throwing {@link RuntimeException}
	 * if it has been.
	 * 
	 * @throws RuntimeException if HTTP header has already been generated
	 */
	private void checkHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException("Header has already been generated.");
		}
	}

	/**
	 * Writes the HTTP header if it hasn't already been written.
	 * 
	 * @throws IOException if I/O error occurs
	 */
	private void writeHeader() throws IOException {
		if (headerGenerated) {
			return;
		}
		
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		sb.append(mimeType.startsWith("text/") ? "; charset=" + encoding : "");
		sb.append("\r\n");
		sb.append(contentLength != null ? "Content-Length: " + contentLength + "\r\n" : "");
		
		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: ");
			sb.append(String.format("%s=\"%s\"", cookie.getName(), cookie.getValue()));
			if (cookie.getDomain() != null) {
				sb.append("; Domain=" + cookie.getDomain());
			}
			if (cookie.getPath() != null) {
				sb.append("; Path=" + cookie.getPath());
			}
			if (cookie.getMaxAge() != null) {
				sb.append("; MaxAge=" + cookie.getMaxAge());
			}
			if (cookie.isHttpOnly()) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		}
		
		sb.append("\r\n");
		
		byte[] bytes = sb.toString().getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(bytes);
		headerGenerated = true;
	}
	
	/**
	 * Model of HTTP cookie.
	 */
	public static class RCCookie {
		
		/**
		 * Name of this cookie.
		 */
		private String name;
		
		/**
		 * Value of this cookie.
		 */
		private String value;
		
		/**
		 * Max-Age property of this cookie.
		 */
		private Integer maxAge;
		
		/**
		 * Domain property of this cookie.
		 */
		private String domain;
		
		/**
		 * Path property of this cookie.
		 */
		private String path;
		
		/**
		 * HttpOnly property of this cookie.
		 */
		private boolean httpOnly;
		
		/**
		 * Constructs a new {@code RCCookie} from the given arguments.
		 * 
		 * @param name     name of this cookie
		 * @param value    value of this cookie
		 * @param maxAge   Max-Age property of this cookie
		 * @param domain   Domain property of this cookie
		 * @param path     Path property of this cookie
		 * @param httpOnly HttpOnly property of this cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, 
				String domain, String path, boolean httpOnly) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}
		
		/**
		 * Constructs a new {@code RCCookie} from the given arguments. HttpOnly
		 * property is set to {@code false}.
		 * 
		 * @param name     name of this cookie
		 * @param value    value of this cookie
		 * @param maxAge   Max-Age property of this cookie
		 * @param domain   Domain property of this cookie
		 * @param path     Path property of this cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, 
				String domain, String path) {
			this(name, value, maxAge, domain, path, false);
		}

		/**
		 * Returns the name of this cookie.
		 * 
		 * @return the name of this cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the value of this cookie.
		 * 
		 * @return the value of this cookie
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Returns the Max-Age property of this cookie.
		 * 
		 * @return the Max-Age property of this cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Returns the Domain property of this cookie.
		 * 
		 * @return the Domain property of this cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the Path property of this cookie.
		 * 
		 * @return the Path property of this cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the HttpOnly property of this cookie.
		 * 
		 * @return the HttpOnly property of this cookie
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}
		
	}

}
