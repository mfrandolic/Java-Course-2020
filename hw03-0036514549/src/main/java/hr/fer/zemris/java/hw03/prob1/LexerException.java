package hr.fer.zemris.java.hw03.prob1;

/**
 * Thrown to indicate that a lexing error has occurred.
 * 
 * @author Matija Frandolić
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code LexerException}.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructs a new {@code LexerException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code LexerException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code LexerException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
