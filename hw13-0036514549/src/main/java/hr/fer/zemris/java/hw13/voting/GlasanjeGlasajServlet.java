package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that records the vote for the band whose link the user has selected.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String resultsFileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-rezultati.txt"
		);
		
		Map<Integer, Integer> votes = BandUtils.getVotes(resultsFileName);
		
		if (req.getParameter("id") != null) {
			int idForUpdate = Integer.parseInt(req.getParameter("id"));
			votes.merge(idForUpdate, 1, Integer::sum);
			BandUtils.writeVotes(resultsFileName, votes);			
		}
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
}
