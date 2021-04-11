package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that is responsible for starting the
 * script which will show the home page with links to other scripts and workers.
 * 
 * @author Matija Frandolić
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getPersistentParameter("bgcolor");
		context.setTemporaryParameter("background", bgcolor == null ? "7F7F7F" : bgcolor);
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
