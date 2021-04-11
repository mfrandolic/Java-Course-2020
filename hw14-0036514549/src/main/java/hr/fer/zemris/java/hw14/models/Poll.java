package hr.fer.zemris.java.hw14.models;

/**
 * Model of a poll with unique ID, title and message.
 * 
 * @author Matija Frandolić
 */
public class Poll {

	/**
	 * Unique ID of this poll.
	 */
	private long id;
	
	/**
	 * Title of this poll.
	 */
	private String title;
	
	/**
	 * Message of this poll.
	 */
	private String message;
	
	/**
	 * Creates a new {@code Poll} from the given information.
	 * 
	 * @param id      unique ID of this poll
	 * @param title   title of this poll
	 * @param message message of this poll
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Creates a new {@code Poll} from the given information. The ID of this
	 * poll is set to zero (0).
	 * 
	 * @param title   title of this poll
	 * @param message message of this poll
	 */
	public Poll(String title, String message) {
		this(0, title, message);
	}
	
	/**
	 * Creates an empty {@code Poll}.
	 */
	public Poll() {
		this(0, null, null);
	}

	/**
	 * Returns the unique ID of this poll.
	 * 
	 * @return the unique ID of this poll
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the unique ID of this poll.
	 * 
	 * @param id the unique ID of this poll
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the title of this poll.
	 * 
	 * @return the title of this poll
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this poll.
	 * 
	 * @param title the title of this poll
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the message of this poll.
	 * 
	 * @return the message of this poll
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of this poll.
	 * 
	 * @param message the message of this poll
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
