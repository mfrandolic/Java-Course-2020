package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a constant double expression.
 * 
 * @author Matija Frandolić
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value of the expression.
	 */
	private double value;
	
	/**
	 * Constructs a new {@code ElementConstantDouble} object
	 * from the given real number.
	 * 
	 * @param value value of the expression
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this element's expression.
	 * 
	 * @return value of this element's expression
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns the string representation of this element's expression.
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Indicates whether two {@code ElementConstantDouble} objects
	 * are equal by comparing their expression values.
	 *
	 * @param  the object with which to compare equality
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ElementConstantDouble)) {
			return false;
		}
		
		ElementConstantDouble other = (ElementConstantDouble) obj;
		
		return Math.abs(value - other.value) < 1E-8;
	}
	
}
