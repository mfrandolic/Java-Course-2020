package hr.fer.zemris.java.hw13.voting;

/**
 * Model of a band with unique integer ID, name, link to a representative song
 * and number of votes.
 * 
 * @author Matija Frandolić
 */
public class Band {
	
	/**
	 * Unique ID of this band.
	 */
	private int id;
	
	/**
	 * Name of this band.
	 */
	private String name;
	
	/**
	 * Link to a representative song of this band.
	 */
	private String link;
	
	/**
	 * Number of votes for this band.
	 */
	private int numberOfVotes;
	
	/**
	 * Creates a new {@code Band} from the given information.
	 * 
	 * @param id            unique ID of this band
	 * @param name          name of this band
	 * @param link          link to a representative song of this band
	 * @param numberOfVotes number of votes for this band
	 */
	public Band(int id, String name, String link, int numberOfVotes) {
		this.id = id;
		this.name = name;
		this.link = link;
		this.numberOfVotes = numberOfVotes;
	}

	/**
	 * Returns unique ID of this band.
	 * 
	 * @return unique ID of this band
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns name of this band.
	 * 
	 * @return name of this band
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns link to a representative song of this band.
	 * 
	 * @return link to a representative song of this band
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Returns number of votes for this band.
	 * 
	 * @return number of votes for this band
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	
	/**
	 * Sets number of votes for this band.
	 * 
	 * @param numberOfVotes number of votes for this band
	 */
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	
}
