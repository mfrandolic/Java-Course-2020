package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that expects path to the file with chart data to be entered as a 
 * single command line argument from which is then {@code BarChart} model
 * created and GUI with painted chart is shown.
 * 
 * @author Matija Frandolić
 */
public class BarChartDemo extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Chart that needs to be painted.
	 */
	private BarChart chart;
	
	/**
	 * Path to the file with chart data.
	 */
	private String path;
	
	/**
	 * Constructs the GUI from the given {@code BarChart} model and path to
	 * the file.
	 * 
	 * @param chart {@code BarChart} model that needs to be painted
	 * @param path  path to the file with chart data
	 */
	public BarChartDemo(BarChart chart, String path) {
		this.chart = chart;
		this.path = path;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		initGUI();
		setSize(500, 500);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the GUI layout and components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		BarChartComponent charComponent = new BarChartComponent(chart);
		charComponent.setBackgroundColor(Color.WHITE);
		charComponent.setBarColor(Color.ORANGE);
		charComponent.setCoordinateSystemColor(Color.BLACK);
		
		cp.add(charComponent, BorderLayout.CENTER);
		cp.add(new JLabel(path, SwingConstants.CENTER), BorderLayout.NORTH);
	}

	/**
	 * Main method of the program that is responsible for starting the GUI.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected path to the file with chart data.");
			return;
		}
		
		Path path = Paths.get(args[0]);
		if (!Files.exists(path) || Files.isDirectory(path)) {
			System.out.println("File doesn't exist or is directory.");
			return;
		}
		
		BarChart chart;
		try {
			chart = parseChartFile(path);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println("I/O error occurred.");
			return;
		}
		
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(chart, args[0]).setVisible(true);
		});
	}
	
	/**
	 * Returns {@code BarChart} object created by parsing the given file.
	 * Only first 6 lines are read. All lines must be in the specified format:
	 * 
	 * <pre>
	 * 1st line: description of the x-axis
	 * 2nd line: description of the y-axis
	 * 3rd line: x1,y1 x2,y2 ... where values are separated by one or more spaces
	 *           and coordinates are separated by comma (without spaces)
	 * 4th line: minimum y-value on the y-axis
	 * 5th line: maximum y-value on the y-axis
	 * 6th line: difference between two closest values on the y-axis
	 * </pre>
	 * 
	 * @param  path path to the file with chart data      
	 * @return      {@code BarChart} object that is created by parsing the file
	 * @throws IOException              if I/O error occurs
	 * @throws IllegalArgumentException if file format is not valid
	 */
	private static BarChart parseChartFile(Path path) throws IOException {
		BufferedReader br = Files.newBufferedReader(path);
		
		String xDescription = br.readLine();
		String yDescription = br.readLine();
		String valuesString = br.readLine();
		String yMinString = br.readLine();
		String yMaxString = br.readLine();
		String deltaString = br.readLine();
		
		if (xDescription == null || yDescription == null || valuesString == null ||
			yMinString == null || yMaxString == null || deltaString == null) {
			throw new IllegalArgumentException("File must contain at least 6 lines.");
		}
		
		String[] valuesArray = valuesString.split("\\s+");
		List<XYValue> valuesList = new ArrayList<>();
		
		for (String value : valuesArray) {
			String[] valueCoordinates = value.split(",");
			
			if (valueCoordinates.length != 2) {
				throw new IllegalArgumentException("Point must have 2 coordinates.");
			}
			
			try {
				int x = Integer.parseInt(valueCoordinates[0]);
				int y = Integer.parseInt(valueCoordinates[1]);
				valuesList.add(new XYValue(x, y));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Point coordinates must be integers.");
			}	
		}

		try {
			int yMin = Integer.parseInt(yMinString);
			int yMax = Integer.parseInt(yMaxString);
			int delta = Integer.parseInt(deltaString);
			return new BarChart(valuesList, xDescription, yDescription, yMin, yMax, delta);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("yMin, yMax and delta must be integers.");
		}
	}
	
}
