package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Thrown to indicate that a lexing error has occurred.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code SmartScriptLexerException}.
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Constructs a new {@code SmartScriptLexerException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code SmartScriptLexerException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code SmartScriptLexerException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
