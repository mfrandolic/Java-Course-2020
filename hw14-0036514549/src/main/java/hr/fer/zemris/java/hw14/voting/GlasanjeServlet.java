package hr.fer.zemris.java.hw14.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which obtains a list of poll options for the poll with {@code id} 
 * equal to the {@code pollID} parameter received through the URL and renders it
 * to the user as a list of clickable links through which the user is able to vote.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			return;
		}
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		
		req.setAttribute("poll", poll);
		req.setAttribute("pollOptions", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
}
