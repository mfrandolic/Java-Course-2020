package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that enables the user to see a list of all bands.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/glasanje")
public class GlasanjeServlet extends HttpServlet {

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
		
		List<Band> bands = BandUtils.getAllBands(definitionFileName);
		bands.sort(Comparator.comparing(Band::getId));
		
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
}
