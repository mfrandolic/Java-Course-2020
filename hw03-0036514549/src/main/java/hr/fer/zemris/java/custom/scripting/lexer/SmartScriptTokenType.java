package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defines possible types of {@code SmartScriptToken}.
 * 
 * @author Matija Frandolić
 */
public enum SmartScriptTokenType {

	/**
	 * End of file.
	 */
	EOF,
	
	/**
	 * Ordinary text.
	 */
	TEXT,
	
	/**
	 * Opening tag.
	 */
	TAG_OPEN,
	
	/**
	 * Closing tag.
	 */
	TAG_CLOSE,
	
	/**
	 * Name of the tag.
	 */
	TAG_NAME,
	
	/**
	 * Variable name.
	 */
	VARIABLE,
	
	/**
	 * String value.
	 */
	STRING,
	
	/**
	 * Function name.
	 */
	FUNCTION,
	
	/**
	 * Operator (+, -, *, / or ^).
	 */
	OPERATOR,
	
	/**
	 * Integer value.
	 */
	INTEGER,
	
	/**
	 * Double value.
	 */
	DOUBLE
	
}
