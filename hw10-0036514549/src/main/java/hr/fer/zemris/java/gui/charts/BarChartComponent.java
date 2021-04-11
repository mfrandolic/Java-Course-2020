package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Component that knows how to display a given {@link BarChart} model by painting
 * it on the canvas of this component.
 * 
 * @author Matija Frandolić
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Chart that is painted.
	 */
	private BarChart chart;
	
	/**
	 * Color of the chart background.
	 */
	private Color backgroundColor = Color.WHITE;
	
	/**
	 * Color of bars on the chart.
	 */
	private Color barColor = Color.BLACK;
	
	/**
	 * Color of the chart coordinate system.
	 */
	private Color coordinateSystemColor = Color.BLACK;
	
	/**
	 * Constructs a new {@code BarChartComponent} from the given {@code BarChart} object.
	 * 
	 * @param chart {@code BarChart} object to be painted by this component
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	/**
	 * Sets the color of the chart background.
	 * 
	 * @param color color of the chart background
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
		repaint();
	}
	
	/**
	 * Sets the color of bars on the chart.
	 * 
	 * @param color color of bars on the chart
	 */
	public void setBarColor(Color color) {
		barColor = color;
		repaint();
	}
	
	/**
	 * Sets the color of the chart coordinate system.
	 * 
	 * @param color color of the chart coordinate system
	 */
	public void setCoordinateSystemColor(Color color) {
		coordinateSystemColor = color;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Dimension size = getSize();
		Insets insets = getInsets();
		int x0 = insets.left;
		int y0 = insets.top;
		int width = size.width - insets.left - insets.right;
		int height = size.height - insets.top - insets.bottom;
		FontMetrics fm = g.getFontMetrics();
				
		g.setColor(backgroundColor);
		g.fillRect(x0, y0, width, height);

		final int SPACE = 5;
		final int TICK_SIZE = 5;
		final int ARROW_SIZE = 10;
		final int EDGE_OFFSET = 20;
		
		int xAxisYCoordinate = height - 2 * fm.getHeight() - 3 * SPACE + insets.bottom;
		int yAxisXCoordinate = x0 + fm.getHeight() + 
			fm.stringWidth(Integer.toString(chart.getYMax())) + 4 * SPACE;
		
		g.setColor(coordinateSystemColor);
		paintDescriptions(g, x0, y0, width, height, fm);
		paintCoordinateSystem(g, xAxisYCoordinate, yAxisXCoordinate, yAxisXCoordinate - TICK_SIZE, 
			width + x0 - EDGE_OFFSET, xAxisYCoordinate + TICK_SIZE, y0 + EDGE_OFFSET, ARROW_SIZE);
		
		final int LAST_TICK_OFFSET = 5;
		int startX = yAxisXCoordinate;
		int endX = width + x0 - EDGE_OFFSET - ARROW_SIZE - LAST_TICK_OFFSET;
		int startY = y0 + EDGE_OFFSET + ARROW_SIZE + LAST_TICK_OFFSET;
		int endY = xAxisYCoordinate;
		paintCoordinateSystemLabelsAndTicks(g, startX, endX, startY, endY, xAxisYCoordinate, 
				                            yAxisXCoordinate, TICK_SIZE, SPACE, fm);
		
		g.setColor(barColor);
		paintBars(g, startX, endX, startY, endY, xAxisYCoordinate, yAxisXCoordinate);
	}
	
	/**
	 * Paints the descriptions of x-axis and y-axis on the given {@code Graphics}
	 * object by using the information from the given arguments.
	 * 
	 * @param g      {@code Graphics} object on which to paint
	 * @param x0     x-coordinate of the coordinate system origin
	 * @param y0     y-coordinate of the coordinate system origin
	 * @param width  width of the canvas
	 * @param height height of the canvas
	 * @param fm     {@code FontMetrics} object that gives information about current font metrics
	 */
	private void paintDescriptions(Graphics g, int x0, int y0, int width, int height, FontMetrics fm) {	
		String xDesc = chart.getXDescription();
		String yDesc = chart.getYDescription();
		
		g.drawString(xDesc, x0 + (width - fm.stringWidth(xDesc)) / 2, y0 + height - fm.getAscent() / 2);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);
        
        g2d.drawString(yDesc, -height - 2 * y0 + (height - fm.stringWidth(yDesc)) / 2, x0 + fm.getAscent());
        
		g2d.setTransform(defaultAt);
	}

	/**
	 * Paints the axes of the chart coordinate system on the given {@code Graphics}
	 * object by using the information from the given arguments.
	 * 
	 * @param g                {@code Graphics} object on which to paint
	 * @param xAxisYCoordinate y-coordinate of the x-axis
	 * @param yAxisXCoordinate x-coordinate of the y-axis
	 * @param startX           x-coordinate of the x-axis starting point
	 * @param endX             x-coordinate of the x-axis ending point
	 * @param startY           y-coordinate of the x-axis starting point
	 * @param endY             y-coordinate of the x-axis ending point
	 * @param arrowSize        size of the coordinate axes arrows
	 */
	private void paintCoordinateSystem(Graphics g, int xAxisYCoordinate, int yAxisXCoordinate, 
			int startX, int endX, int startY, int endY, int arrowSize) {
		
		g.drawLine(startX, xAxisYCoordinate, endX - arrowSize, xAxisYCoordinate);
		g.drawLine(yAxisXCoordinate, startY, yAxisXCoordinate, endY + arrowSize);
		
		g.fillPolygon(
			new int[] {endX - arrowSize, endX - arrowSize, endX}, 
			new int[] {
				xAxisYCoordinate - arrowSize / 2, 
				xAxisYCoordinate + arrowSize / 2, 
				xAxisYCoordinate}, 
			3
		);
		
		g.fillPolygon(
			new int[] {
				yAxisXCoordinate - arrowSize / 2, 
				yAxisXCoordinate + arrowSize / 2, 
				yAxisXCoordinate}, 
			new int[] {endY + arrowSize, endY + arrowSize, endY}, 
			3
		);
	}
	
	/**
	 * Paints the labels and ticks of the chart coordinate system on the given 
	 * {@code Graphics} object by using the information from the given arguments.
	 * 
	 * @param g                {@code Graphics} object on which to paint
	 * @param startX           x-coordinate of the x-axis starting point
	 * @param endX             x-coordinate of the x-axis ending point
	 * @param startY           y-coordinate of the x-axis starting point
	 * @param endY             y-coordinate of the x-axis ending point
	 * @param xAxisYCoordinate y-coordinate of the x-axis
	 * @param yAxisXCoordinate x-coordinate of the y-axis
	 * @param tickSize         length of ticks on coordinate axes
	 * @param space            amount of space between coordinate axes and labels
	 * @param fm               {@code FontMetrics} object that gives information about current font metrics
	 */
	private void paintCoordinateSystemLabelsAndTicks(Graphics g, int startX, int endX, int startY, int endY,
			int xAxisYCoordinate, int yAxisXCoordinate, int tickSize, int space, FontMetrics fm) {
		
		int numberOfTicksX = chart.getValues().size();
		double spaceBetweenX = (double) (endX - startX) / numberOfTicksX;
		
		double x = startX + spaceBetweenX;
		for (int i = 0; i < numberOfTicksX; i++) {
			g.drawLine((int) x, xAxisYCoordinate, (int) x, xAxisYCoordinate + tickSize);
			
			String label = Integer.toString(chart.getValues().get(i).getX());
			g.drawString(label, (int) (x - spaceBetweenX / 2 - fm.stringWidth(label) / 2), 
					     xAxisYCoordinate + fm.getHeight() + space);
			
			x += spaceBetweenX;
		}
		
		int numberOfTicksY = (chart.getYMax() - chart.getYMin()) / chart.getDelta();
		double spaceBetweenY = (double) (endY - startY) / numberOfTicksY;
		
		int yValue = chart.getYMax();
		double y = startY;
		for (int i = 0; i <= numberOfTicksY; i++) {
			if (i < numberOfTicksY) {
				g.drawLine(yAxisXCoordinate - tickSize, (int) y, yAxisXCoordinate, (int) y);
			}
			
			String label = Integer.toString(yValue);				
			g.drawString(label, yAxisXCoordinate - space - tickSize - fm.stringWidth(label), 
					    (int) (y - fm.getDescent() + fm.getHeight() / 2));	
			
			y += spaceBetweenY;
			yValue -= chart.getDelta();
		}
	}
	
	/**
	 * Paints bars of the chart on the given {@code Graphics} object by using 
	 * the information from the given arguments.
	 * 
	 * @param g                {@code Graphics} object on which to paint
	 * @param startX           x-coordinate of the x-axis starting point
	 * @param endX             x-coordinate of the x-axis ending point
	 * @param startY           y-coordinate of the x-axis starting point
	 * @param endY             y-coordinate of the x-axis ending point
	 * @param xAxisYCoordinate y-coordinate of the x-axis
	 * @param yAxisXCoordinate x-coordinate of the y-axis
	 */
	private void paintBars(Graphics g, int startX, int endX, int startY, int endY, 
			int xAxisYCoordinate, int yAxisXCoordinate) {
		
		final int GAP = 10;
		int numberOfTicksX = chart.getValues().size();
		double spaceBetweenX = (double) (endX - startX) / numberOfTicksX;
		double x = yAxisXCoordinate + GAP;
		
		for (XYValue value : chart.getValues()) {
			double barHeight = (double) value.getY() / chart.getYMax() * (endY - startY);
			g.fillRect((int) x, (int) (xAxisYCoordinate - barHeight), 
					   (int) (spaceBetweenX - 2 * GAP), (int) barHeight);
			x += spaceBetweenX;
		}
	}
	
}
