package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet that creates image showing a pie chart with voting results. The resulting 
 * image is in PNG format.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

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
			
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();
		
		PieDataset dataset = createDataset(definitionFileName, resultsFileName);
		JFreeChart chart = createChart(dataset, "Voting results");
		
		final int width = 800;
		final int height = 500;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}
	
    /**
     * Creates dataset about the number of votes that each band received and 
     * returns resulting {@link PieDataset}. 
     * 
     * @param  definitionFileName path to the file with definition of all bands
	 * @param  resultsFileName    path to the file with stored votes for bands
     * @return                    resulting {@code PieDataset} with information about
     *                            the number of votes for each band
     * @throws IOException        if I/O error occurs
     */
    private static PieDataset createDataset(String definitionFileName, 
    		String resultsFileName) throws IOException {
    	
        DefaultPieDataset result = new DefaultPieDataset();
        List<Band> bands = BandUtils.getBandsAndVotes(definitionFileName, resultsFileName);
		
		for (Band band : bands) {
			result.setValue(band.getName(), band.getNumberOfVotes());			
		}
        
        return result;
    }
	
	/**
	 * Creates and returns {@link JFreeChart} that is created from the given
	 * dataset and with the given title.
	 * 
	 * @param dataset dataset to be used for chart creation
	 * @param title   title of the chart
	 * @return        {@code JFreeChart} created from the given dataset and with
	 *                the given title
	 */
	private static JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
		
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        
        return chart;
	}
	
}
