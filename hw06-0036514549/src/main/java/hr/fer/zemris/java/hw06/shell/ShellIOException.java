package hr.fer.zemris.java.hw06.shell;

/**
 * Thrown to indicate that shell I/O error occurred.
 * 
 * @author Matija Frandolić
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code ShellIOException}.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Constructs a new {@code ShellIOException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code ShellIOException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code ShellIOException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
