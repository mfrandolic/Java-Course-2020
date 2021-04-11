package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a variable expression.
 * 
 * @author Matija Frandolić
 */
public class ElementVariable extends Element {

	/**
	 * Name of the variable expression.
	 */
	private String name;
	
	/**
	 * Constructs a new {@code ElementVariable} object
	 * from the given name of the variable expression.
	 * 
	 * @param name name of the variable expression
	 */
	public ElementVariable(String name) {
		this.name = Objects.requireNonNull(name, "Name of the variable cannot be null.");
	}
	
	/**
	 * Returns the name of this element's variable expression.
	 * 
	 * @return the name of this element's variable expression
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the string representation of this element's expression. 
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Indicates whether two {@code ElementVariable} objects
	 * are equal by comparing their names.
	 *
	 * @param obj the object with which to compare equality
	 * @return    {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ElementVariable)) {
			return false;
		}
		
		ElementVariable other = (ElementVariable) obj;
		
		return name.equals(other.name);
	}
	
}
