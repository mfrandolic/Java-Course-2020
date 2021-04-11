package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown to indicate that a problem with layout occurred.
 * 
 * @author Matija Frandolić
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code CalcLayoutException}.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructs a new {@code CalcLayoutException} with the given message
	 * that is used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code CalcLayoutException} with the given cause
	 * that is used to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs a new {@code CalcLayoutException} with the given message
	 * and cause that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
