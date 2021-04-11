package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class which contains a method for forwarding {@link HttpServletRequest}s
 * to an error page.
 * 
 * @author Matija Frandolić
 */
public class ServletUtil {

	/**
	 * Forwards the request to an error page with the given message.
	 * 
	 * @param  req     an {@link HttpServletRequest} object that contains the 
	 *                 request the client has made of the servlet
	 * @param  resp    an {@link HttpServletResponse} object that contains the 
	 *                 response the servlet sends to the client
	 * @param  message error message to display
	 * @throws ServletException is request could not be handled
	 * @throws IOException      if I/O error occurred
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp, 
			String message) throws ServletException, IOException {
		
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}
	
}
