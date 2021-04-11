package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

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
 * Servlet that creates image showing a pie chart depicting sample information
 * about OS usage. The resulting image is in PNG format.
 * 
 * @author Matija Frandolić
 */
@WebServlet(urlPatterns="/reportImage")
public class ReportServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		OutputStream outputStream = resp.getOutputStream();
		
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "OS usage");
		
		final int width = 800;
		final int height = 500;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}
	
    /**
     * Creates sample dataset about OS usage and returns resulting {@link PieDataset}. 
     * 
     * @return resulting {@code PieDataset} about OS usage
     */
    private static PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        
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
