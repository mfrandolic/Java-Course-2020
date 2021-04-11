package hr.fer.zemris.java.gui.layouts;

/**
 * Model of the constraint used by {@link CalcLayout}. Each constraint specifies 
 * row and column in the layout grid of the layout manager.
 * 
 * @author Matija Frandolić
 */
public class RCPosition {
	
	/**
	 * Row in the layout grid.
	 */
	private int row;
	
	/**
	 * Column in the layout grid.
	 */
	private int column;
	
	/**
	 * Constructs a new {@code RCPosition} from the given row and column.
	 * 
	 * @param row    row in the layout grid
	 * @param column column in the layout grid
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Returns row in the layout grid.
	 * 
	 * @return row in the layout grid
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns column in the layout grid.
	 * 
	 * @return column in the layout grid
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Creates new {@code RCPosition} constraint by parsing the given string.
	 * String must be in the format: "row,grid" (without spaces).
	 * 
	 * @param  text the string that is parsed
	 * @return      new {@code RCPosition} constraint created by parsing the given string
	 * @throws IllegalArgumentException if the format of the string is invalid or if
	 *                                  row or column are not integers
	 */
	public static RCPosition parse(String text) {
		String[] split = text.split(",");
		if (split.length != 2) {
			throw new IllegalArgumentException("Invalid position format.");
		}
		
		int row;
		int column;
		try {
			row = Integer.parseInt(split[0]);
			column = Integer.parseInt(split[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Row and column must be integers.");
		}
		
		return new RCPosition(row, column);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RCPosition)) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if (row == other.row && column == other.column) {
			return true;
		} else {
			return false;
		}
	}
	
}
