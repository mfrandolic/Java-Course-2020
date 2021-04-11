package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Represent a token of {@code Lexer}. Token is a pair of 
 * {@link TokenType} object and any object that represents
 * value of the token.
 * 
 * @author Matija Frandolić
 */
public class Token {
	
	/**
	 * Type of this token.
	 */
	private TokenType type;
	
	/**
	 * Value of this token.
	 */
	private Object value;

	/**
	 * Constructs a new {@code Token} object from the given
	 * type and value.
	 * 
	 * @param type  type of this token
	 * @param value value of this token
	 */
	public Token(TokenType type, Object value) {
		this.type = Objects.requireNonNull(type, "Token type cannot be null.");
		this.value = value;
	}
	
	/**
	 * Returns type of this token.
	 * 
	 * @return type of this token
	 */
	public TokenType getType() {
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
