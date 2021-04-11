package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementation of {@link IWebWorker} that outputs the parameters it obtained
 * formatted as an HTML table.
 * 
 * @author Matija Frandolić
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head><title>Parameters</title></head>");
		sb.append("<body>");
		sb.append("<table border=1>");
		sb.append("<thead>");
		sb.append("<tr><th>Parameter name</th><th>Parameter value</th></tr>");
		sb.append("</thead>");
		sb.append("<tbody>");
		
		for (String key : context.getParameterNames()) {
			String value = context.getParameter(key);
			sb.append(String.format("<tr><td>%s</td><td>%s</td></tr>", key, value));
		}
		
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		
		try {
			context.write(sb.toString());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
