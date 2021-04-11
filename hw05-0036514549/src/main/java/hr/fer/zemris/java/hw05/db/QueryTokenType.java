package hr.fer.zemris.java.hw05.db;

/**
 * Defines possible types of {@code QueryToken}.
 * 
 * @author Matija Frandolić
 */
public enum QueryTokenType {

	/**
	 * Attribute name.
	 */
	ATTRIBUTE,
	
	/**
	 * String literal.
	 */
	STRING_LITERAL,
	
	/**
	 * Operator.
	 */
	OPERATOR,
	
	/**
	 * And operator.
	 */
	AND_OPERATOR,
	
	/**
	 * End of file.
	 */
	EOF
	
}
