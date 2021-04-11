package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which invalidates current session and sends redirect to 
 * {@code /servleti/main}.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
	
}
