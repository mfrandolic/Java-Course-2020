package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that the used stack is empty.
 * 
 * @author Matija Frandolić
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code EmptyStackException}.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructs a new {@code EmptyStackException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code EmptyStackException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs a new {@code EmptyStackException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
