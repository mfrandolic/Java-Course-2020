package hr.fer.zemris.java.hw14.dao;

/**
 * Thrown to indicate that {@link DAO} access error has occurred.
 */
public class DAOException extends RuntimeException {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code DAOException}.
	 */
	public DAOException() {
		super();
	}

	/**
	 * Constructs a new {@code DAOException} with the specified detail message, 
	 * cause, suppression enabled or disabled, and writable stack trace enabled 
	 * or disabled.
	 * 
	 * @param message            the message that is used to give more details
	 * @param cause              the cause of the exception that is used to give 
	 *                           more details
	 * @param enableSuppression  whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs a new {@code DAOException} with the given message and cause 
	 * that are used to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 * @param cause   the cause of the exception that is used to give more details 
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new {@code DAOException} with the given message that is used 
	 * to give more details about the exception.
	 * 
	 * @param message the message that is used to give more details
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code DAOException} with the given cause that is used 
	 * to give more details about the exception.
	 * 
	 * @param cause the cause of the exception that is used to give more details
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
	
}
