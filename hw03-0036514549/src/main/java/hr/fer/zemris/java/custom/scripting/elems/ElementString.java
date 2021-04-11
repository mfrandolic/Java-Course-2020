package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a string expression.
 * 
 * @author Matija Frandolić
 */
public class ElementString extends Element {

	/**
	 * Value of the expression.
	 */
	private String value;
	
	/**
	 * Constructs a new {@code ElementString} object
	 * from the given value of the string expression.
	 * 
	 * @param value value of the string expression
	 */
	public ElementString(String value) {
		this.value = Objects.requireNonNull(value, "Value of the string cannot be null.");
	}
	
	/**
	 * Returns the value of this element's string expression.
	 * 
	 * @return value of this element's string expression
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the string representation of this element's expression.
	 * The following character sequences are replaced: "\\" by "\\\\", 
	 * "\"" by "\\\"", "\n" by "\\n", "\r" by "\\r", "\t" by "\\t".
	 * Enclosing double quotes are also added.
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return "\"" + 
			value.replace("\\", "\\\\")
		         .replace("\"", "\\\"")
		         .replace("\n", "\\n")
		         .replace("\r", "\\r")
		         .replace("\t", "\\t") + "\"";
	}
	
	/**
	 * Indicates whether two {@code ElementString} objects
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
		
		if (!(obj instanceof ElementString)) {
			return false;
		}
		
		ElementString other = (ElementString) obj;
		
		return value.equals(other.value);
	}
	
}
