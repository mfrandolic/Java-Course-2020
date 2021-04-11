package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that updates background color of the 
 * home page, depending on the received parameter with name "bgcolor", and
 * generates message that color was updated or that it wasn't.
 * 
 * @author Matija Frandolić
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		
		boolean updated = false;
		if (bgcolor.matches("[0-9a-fA-f]{6}")) {
			context.setPersistentParameter("bgcolor", bgcolor);
			updated = true;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head><title>Set background color</title></head>");
		sb.append("<body>");
		sb.append("<p><a href=\"/index2.html\">Back to Home</a></p>");
		sb.append("<p>Background color was " + (!updated ? "not" : "") + " updated.</p>");
		sb.append("</body>");
		sb.append("</html>");
		
		context.setMimeType("text/html");
		try {
			context.write(sb.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
