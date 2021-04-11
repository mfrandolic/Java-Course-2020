package hr.fer.zemris.java.hw05.db;

/**
 * Thrown to indicate that a parsing error has occurred.
 * 
 * @author Matija Frandolić
 */
public class QueryParserException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code QueryParserException}.
	 */
	public QueryParserException() {
		super();
	}
	
	/**
	 * Constructs a new {@code QueryParserException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public QueryParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code QueryParserException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code QueryParserException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

