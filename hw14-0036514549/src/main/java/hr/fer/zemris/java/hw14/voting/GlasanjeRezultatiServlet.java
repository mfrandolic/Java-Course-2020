package hr.fer.zemris.java.hw14.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which obtains a list of poll options for the poll with {@code id} 
 * equal to the {@code pollID} parameter received through the URL and renders it
 * to the user as a table that represents a report on voting results.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

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
		
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		
		if (pollOptions.isEmpty()) {
			return;
		}
		
		pollOptions.sort(
			Comparator.comparing(PollOption::getVotesCount).reversed()
			.thenComparing(PollOption::getOptionTitle)
		);
		
		long mostVotes = pollOptions.get(0).getVotesCount();
		List<PollOption> pollOptionsWithMostVotes = new ArrayList<>();
		
		for (PollOption pollOption : pollOptions) {
			if (pollOption.getVotesCount() >= mostVotes) {
				pollOptionsWithMostVotes.add(pollOption);				
			}
		}

		req.setAttribute("pollID", pollID);
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("pollOptionsWithMostVotes", pollOptionsWithMostVotes);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
