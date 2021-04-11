package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * An interface to the data persistence layer. Methods are provided for working
 * with {@link Poll} and {@link PollOption} objects.
 * 
 * @author Matija Frandolić
 */
public interface DAO {

	/**
	 * Returns {@link Poll} object with the given {@code id}.
	 * 
	 * @param  id           id of the poll to be returned
	 * @return              {@code Poll} object with the given {@code id}
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Returns a list of all {@link Poll}s.
	 * 
	 * @return              a list of all {@code Poll}s
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public List<Poll> getPolls() throws DAOException;	
	
	/**
	 * Returns {@link PollOption} object with the given {@code id}.
	 * 
	 * @param  id           id of the poll option to be returned
	 * @return              {@code PollOption} object with the given {@code id}
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public PollOption getPollOption(long id) throws DAOException;
	
	/**
	 * Returns a list of all {@link PollOption}s with the given {@code pollID}.
	 * 
	 * @param  pollID       pollID based on which to filter poll options
	 * @return              a list of all {@code PollOption}s with the 
	 *                      given {@code pollID}
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public List<PollOption> getPollOptions(long pollID) throws DAOException;
	
	/**
	 * Increments the number of votes for the {@link PollOption} object with 
	 * the given {@code id}.
	 * 
	 * @param  id           id of the poll option for which to increment 
	 *                      the number of votes
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public void incrementVotesCount(long id) throws DAOException;
	
}
