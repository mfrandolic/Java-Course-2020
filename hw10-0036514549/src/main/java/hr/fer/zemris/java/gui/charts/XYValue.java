package hr.fer.zemris.java.gui.charts;

/**
 * Model of the value used by {@link BarChart}.
 * 
 * @author Matija Frandolić
 */
public class XYValue {

	/**
	 * X-component of this value.
	 */
	private int x;
	
	/**
	 * Y-component of this value.
	 */
	private int y;
	
	/**
	 * Constructs a new {@code XYValue} with given components.
	 * 
	 * @param x x-component of this value
	 * @param y y-component of this value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x-component of this value.
	 * 
	 * @return the x-component of this value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y-component of this value.
	 * 
	 * @return the y-component of this value
	 */
	public int getY() {
		return y;
	}

}
