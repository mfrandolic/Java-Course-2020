package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that sets session attribute {@code pickedBgCol} to the value of the 
 * request parameter {@code color} and redirects to {@code colors.jsp}.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/setcolor")
public class ColorServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		session.setAttribute("pickedBgCol", req.getParameter("color"));
		resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/colors.jsp"));
	}
	
}
