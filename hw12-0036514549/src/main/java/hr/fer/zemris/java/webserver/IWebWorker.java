package hr.fer.zemris.java.webserver;

/**
 * Model of an object that can process the given request and create content
 * for the client.
 * 
 * @author Matija Frandolić
 */
public interface IWebWorker {

	/**
	 * Processes the given request and creates content for the client.
	 * 
	 * @param  context   request context to process
	 * @throws Exception if any exception occurs
	 */
	void processRequest(RequestContext context) throws Exception;
	
}
