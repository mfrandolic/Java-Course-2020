package hr.fer.zemris.java.hw14.voting;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
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

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which creates image showing a pie chart with voting results for the poll 
 * with {@code id} equal to the {@code pollID} parameter received through the URL. 
 * The resulting image is in PNG format.
 * 
 * @author Matija Frandolić
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		long pollId;
		try {
			pollId = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			return;
		}
		
		PieDataset dataset = createDataset(pollId);
		JFreeChart chart = createChart(dataset, "Voting results");
		
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();
		
		final int width = 800;
		final int height = 500;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}
	
    /**
     * Returns the {@link PieDataset} about the number of votes that each
     * poll option with the given {@code pollID} has received. 
     * 
     * @param  pollID      id of the corresponding poll for which to show poll options
     * @return             resulting {@code PieDataset} with information about the number 
     *                     of votes for each poll option with the given {@code pollID}
     * @throws IOException if I/O error occurs
     */
    private static PieDataset createDataset(long pollID) throws IOException {
        DefaultPieDataset result = new DefaultPieDataset();
        
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		pollOptions.sort(
			Comparator.comparing(PollOption::getVotesCount).reversed()
			.thenComparing(PollOption::getOptionTitle)
		);
        
		for (PollOption pollOption : pollOptions) {
			result.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount());			
		}
        
        return result;
    }
	
	/**
	 * Creates and returns {@link JFreeChart} that is created from the given
	 * {@link PieDataset} and with the given title.
	 * 
	 * @param dataset {@code PieDataset} to be used for chart creation
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
