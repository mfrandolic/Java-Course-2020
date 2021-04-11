package hr.fer.zemris.java.hw03.prob1;

/**
 * Defines possible types of {@code Token}s.
 * 
 * @author Matija Frandolić
 */
public enum TokenType {
	
	/**
	 * End of file.
	 */
	EOF,
	
	/**
	 * A single word.
	 */
	WORD,
	
	/**
	 * An integer.
	 */
	NUMBER,
	
	/**
	 * A single symbol.
	 */
	SYMBOL
	
}
