package hr.fer.zemris.java.webserver;

/**
 * Model of an object responsible for dispatching received request based
 * on the URL of the requested resource.
 * 
 * @author Matija Frandolić
 */
public interface IDispatcher {

	/**
	 * Dispatches the request based on the URL of the requested resource.
	 * 
	 * @param  urlPath URL of the requested resource
	 * @throws Exception if any exception occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}
