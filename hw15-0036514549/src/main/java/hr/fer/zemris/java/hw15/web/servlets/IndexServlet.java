package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which sends redirect to {@code /servleti/main}.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
	
}
