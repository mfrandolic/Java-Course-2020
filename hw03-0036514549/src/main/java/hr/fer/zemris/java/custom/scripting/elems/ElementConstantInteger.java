package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a constant integer expression.
 * 
 * @author Matija Frandolić
 */
public class ElementConstantInteger extends Element {

	/**
	 * Value of the expression.
	 */
	private int value;
	
	/**
	 * Constructs a new {@code ElementConstantInteger} object
	 * from the given integer.
	 * 
	 * @param value value of the expression
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this element's expression.
	 * 
	 * @return value of this element's expression
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the string representation of this element's expression.
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Indicates whether two {@code ElementConstantInteger} objects
	 * are equal by comparing their expression values.
	 *
	 * @param obj the object with which to compare equality
	 * @return    {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ElementConstantInteger)) {
			return false;
		}
		
		ElementConstantInteger other = (ElementConstantInteger) obj;
		
		return value == other.value;
	}
	
}
