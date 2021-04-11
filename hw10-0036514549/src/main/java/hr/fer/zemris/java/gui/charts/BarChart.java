package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Model of a bar chart. Each chart has description of x-axis and y-axis, 
 * {@link XYValue}s that are shown on the chart, maximum and minimum y value on
 * the y-axis, and delta that represents difference between two closest y values 
 * on the y-axis.
 * 
 * @author Matija Frandolić
 */
public class BarChart {

	/**
	 * Values to be shown by this chart.
	 */
	private List<XYValue> values;
	
	/**
	 * Description of the x-axis.
	 */
	private String xDescription;
	
	/**
	 * Description of the y-axis.
	 */
	private String yDescription;
	
	/**
	 * Minimum value on the y-axis.
	 */
	private int yMin;
	
	/**
	 * Maximum value on the y-axis.
	 */
	private int yMax;
	
	/**
	 * Difference between two closest values on the y-axis.
	 */
	private int delta;
	
	/**
	 * Constructs a new {@code BarChart} from the given arguments.
	 * 
	 * @param  values       values to be shown by this chart
	 * @param  xDescription description of the x-axis
	 * @param  yDescription description of the y-axis
	 * @param  yMin         minimum value on the y-axis
	 * @param  yMax         maximum value on the y-axis
	 * @param  delta        difference between two closest values on the y-axis
	 * @throws IllegalArgumentException if yMin is negative or yMax is not greater
	 *                                  than yMin or some y-values are less than yMin
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, 
			int yMin, int yMax, int delta) {
		
		if (yMin < 0) {
			throw new IllegalArgumentException("yMin cannot be negative.");
		}
		if (yMin >= yMax) {
			throw new IllegalArgumentException("yMax must be greater than yMin.");
		}
		for (XYValue value : values) {
			if (value.getY() < yMin) {
				throw new IllegalArgumentException("Some y-values are less than yMin.");
			}
		}
		
		while ((yMax - yMin) % delta != 0) {
			yMax += 1;
		}
		
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.delta = delta;
	}

	/**
	 * Returns values to be shown by this chart.
	 * 
	 * @return values to be shown by this chart
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns description of the x-axis.
	 * 
	 * @return description of the x-axis
	 */
	public String getXDescription() {
		return xDescription;
	}

	/**
	 * Returns description of the y-axis.
	 * 
	 * @return description of the y-axis
	 */
	public String getYDescription() {
		return yDescription;
	}

	/**
	 * Returns minimum value on the y-axis.
	 * 
	 * @return minimum value on the y-axis
	 */
	public int getYMin() {
		return yMin;
	}

	/**
	 * Returns maximum value on the y-axis.
	 * 
	 * @return maximum value on the y-axis
	 */
	public int getYMax() {
		return yMax;
	}

	/**
	 * Returns difference between two closest values on the y-axis.
	 * 
	 * @return difference between two closest values on the y-axis
	 */
	public int getDelta() {
		return delta;
	}
	
}
