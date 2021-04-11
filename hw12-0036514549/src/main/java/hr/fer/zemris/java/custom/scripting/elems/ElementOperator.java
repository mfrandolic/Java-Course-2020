package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents an operator expression.
 * 
 * @author Matija Frandolić
 */
public class ElementOperator extends Element {

	/**
	 * Symbol of the operator.
	 */
	private String symbol;
	
	/**
	 * Constructs a new {@code ElementOperator} object
	 * from the given symbol of the operator.
	 * 
	 * @param symbol symbol of the operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = Objects.requireNonNull(symbol, "Symbol of the operator cannot be null.");
	}
	
	/**
	 * Returns the symbol of this element's operator.
	 * 
	 * @return the symbol of this element's operator
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns the string representation of this element's expression.
	 * 
	 * @return the string representation of this element's expression
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Indicates whether two {@code ElementOperator} objects
	 * are equal by comparing their symbols.
	 *
	 * @param obj the object with which to compare equality
	 * @return    {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ElementOperator)) {
			return false;
		}
		
		ElementOperator other = (ElementOperator) obj;
		
		return symbol.equals(other.symbol);
	}
	
}
