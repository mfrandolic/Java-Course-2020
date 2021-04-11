package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Represents a token of {@code SmartScriptLexer}. Token is a pair of 
 * {@link SmartScriptTokenType} object and any object that represents
 * value of the token.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptToken {

	/**
	 * Type of this token.
	 */
	private SmartScriptTokenType type;
	
	/**
	 * Value of this token.
	 */
	private Object value;
	
	/**
	 * Constructs a new {@code SmartScriptToken} object from the given
	 * type and value.
	 * 
	 * @param type  type of this token
	 * @param value value of this token
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = Objects.requireNonNull(type, "Token type cannot be null.");
		this.value = value;
	}
	
	/**
	 * Returns type of this token.
	 * 
	 * @return type of this token
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
	
	/**
	 * Returns value of this token or {@code null} if value is not defined.
	 * 
	 * @return value of this token or {@code null} if value is not defined
	 */
	public Object getValue() {
		return value;
	}
	
}
