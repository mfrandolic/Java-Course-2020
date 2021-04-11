package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that a parsing error has occurred.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code SmartScriptParserException}.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructs a new {@code SmartScriptParserException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code SmartScriptParserException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs a new {@code SmartScriptParserException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
