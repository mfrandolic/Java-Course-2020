package hr.fer.zemris.java.hw14.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which records a vote for the poll option with {@code id} equal to 
 * the {@code id} parameter received through the URL.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		long id;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {
			return;
		}
		
		DAOProvider.getDao().incrementVotesCount(id);
		PollOption pollOption = DAOProvider.getDao().getPollOption(id);
		
		if (pollOption == null) {
			return;
		}
		
		resp.sendRedirect(req.getContextPath() 
			+ "/servleti/glasanje-rezultati?pollID=" + pollOption.getPollID()
		);
	}
	
}
