package hr.fer.zemris.java.hw14.models;

/**
 * Model of a poll option with unique ID, title, link to a resource, ID of the 
 * corresponding poll and number of votes.
 * 
 * @author Matija Frandolić
 */
public class PollOption {

	/**
	 * Unique ID of this poll option.
	 */
	private long id;
	
	/**
	 * Title of this poll option.
	 */
	private String optionTitle;
	
	/**
	 * Link to a resource associated with this poll option.
	 */
	private String optionLink;
	
	/**
	 * ID of the corresponding poll to which this poll option belongs.
	 */
	private long pollID;
	
	/**
	 * Number of votes for this poll option.
	 */
	private long votesCount;
	
	/**
	 * Creates a new {@code PollOption} from the given information.
	 * 
	 * @param id          unique ID of this poll option
	 * @param optionTitle title of this poll option
	 * @param optionLink  link to a resource associated with this poll option
	 * @param pollID      ID of the corresponding poll to which this poll option belongs
	 * @param votesCount  number of votes for this poll option
	 */
	public PollOption(long id, String optionTitle, String optionLink, 
	                  long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}
	
	/**
	 * Creates a new {@code PollOption} from the given information. The ID of this
	 * poll option is set to zero (0).
	 * 
	 * @param optionTitle title of this poll option
	 * @param optionLink  link to a resource associated with this poll option
	 * @param pollID      ID of the corresponding poll to which this poll option belongs
	 * @param votesCount  number of votes for this poll option
	 */
	public PollOption(String optionTitle, String optionLink, long pollID, 
	                  long votesCount) {
		this(0, optionTitle, optionLink, pollID, votesCount);
	}

	/**
	 * Creates an empty {@code PollOption}.
	 */
	public PollOption() {
		this(0, null, null, 0, 0);
	}

	/**
	 * Returns the unique ID of this poll option.
	 * 
	 * @return the unique ID of this poll option
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the unique ID of this poll option.
	 * 
	 * @param id the unique ID of this poll option
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the title of this poll option.
	 * 
	 * @return the title of this poll option
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the title of this poll option.
	 * 
	 * @param optionTitle the title of this poll options
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns the link to a resource associated with this poll option.
	 * 
	 * @return the link to a resource associated with this poll option
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the link to a resource associated with this poll option.
	 * 
	 * @param optionLink the link to a resource associated with this poll option
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Returns the ID of the corresponding poll to which this poll option belongs.
	 * 
	 * @return ID of the corresponding poll to which this poll option belongs
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Sets ID of the corresponding poll to which this poll option belongs.
	 * 
	 * @param pollID ID of the corresponding poll to which this poll option belongs
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Returns the number of votes for this poll option.
	 * 
	 * @return the number of votes for this poll option
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the number of votes for this poll option.
	 * 
	 * @param votesCount the number of votes for this poll option
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
}
