package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet which shows to the user a list of all entries for the specified author.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/entries")
public class EntriesListServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		if (req.getAttribute("author") == null) {
			ServletUtil.sendError(req, resp, "Forbidden.");
			return;
		}
		
		String nick = ((BlogUser) req.getAttribute("author")).getNick();
		
		List<BlogEntry> entries = DAOProvider.getDAO().getEntriesForAuthor(nick);
		req.setAttribute("entries", entries);
		req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
	}
	
}
