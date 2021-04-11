package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents a function expression.
 * 
 * @author Matija Frandolić
 */
public class ElementFunction extends Element {

	/**
	 * Name of the function expression.
	 */
	private String name;
	
	/**
	 * Constructs a new {@code ElementFunction} object 
	 * from the given name of the function expression.
	 * 
	 * @param name name of the function expression
	 */
	public ElementFunction(String name) {
		this.name = Objects.requireNonNull(name, "Name of the function cannot be null.");
	}
	
	/**
	 * Returns the name of this element's function expression.
	 * 
	 * @return the name of this element's function expression
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the string representation of this element's expression. 
	 * The format of the string is: {@code "@" + name}, where name is 
	 * the name of this element's function expression
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
	
	/**
	 * Indicates whether two {@code ElementFunction} objects
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
		
		if (!(obj instanceof ElementFunction)) {
			return false;
		}
		
		ElementFunction other = (ElementFunction) obj;
		
		return name.equals(other.name);
	}
	
}
