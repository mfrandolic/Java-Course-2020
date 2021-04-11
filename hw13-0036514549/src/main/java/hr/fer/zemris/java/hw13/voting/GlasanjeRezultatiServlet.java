package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that is responsible for displaying a report with voting results.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String definitionFileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-definicija.txt"
		);
		String resultsFileName = req.getServletContext().getRealPath(
			"/WEB-INF/glasanje-rezultati.txt"
		);
					
		List<Band> bands = BandUtils.getBandsAndVotes(definitionFileName, resultsFileName);
		
		int mostVotes = bands.get(0).getNumberOfVotes();
		List<Band> bandsWithMostVotes = new ArrayList<>();
		
		if (mostVotes > 0) {
			for (Band band : bands) {
				if (band.getNumberOfVotes() >= mostVotes) {
					bandsWithMostVotes.add(band);				
				}
			}
		}

		req.setAttribute("bands", bands);
		req.setAttribute("bandsWithMostVotes", bandsWithMostVotes);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
