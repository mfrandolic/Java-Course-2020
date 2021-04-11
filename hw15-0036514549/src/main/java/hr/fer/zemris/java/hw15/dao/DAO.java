package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * An interface to the data persistence layer. Methods are provided for working
 * with {@link BlogEntry}, {@link BlogEntry} and {@link BlogUser} objects.
 * 
 * @author Matija Frandolić
 */
public interface DAO {

	/**
	 * Returns {@link BlogEntry} with the given {@code id} or {@code null} if
	 * such entry does not exist.
	 * 
	 * @param  id           id of the entry to be returned
	 * @return              {@code BlogEntry} with the given {@code id} or
	 *                      {@code null} if such entry does not exist
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	BlogEntry getEntry(Long id) throws DAOException;
	
	/**
	 * Returns a list of {@link BlogEntry} objects for author with the given
	 * {@code nick}.
	 * 
	 * @param  nick         author's {@code nick}
	 * @return              a list of {@code BlogEntry} objects for author 
	 *                      with the given {@code nick}
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	List<BlogEntry> getEntriesForAuthor(String nick) throws DAOException;
	
	/**
	 * Saves the given {@link BlogEntry}.
	 * 
	 * @param  entry        entry to be saved
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	void addBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Returns {@link BlogUser} with the given {@code nick} or {@code null} if
	 * such user does not exist.
	 * 
	 * @param  nick         user's nick
	 * @return              {@code BlogUser} with the given {@code nick} or
	 *                      {@code null} if such user does not exist
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	BlogUser getUser(String nick) throws DAOException;
	
	/**
	 * Returns a list of all {@link BlogUser}s.
	 * 
	 * @return              a list of all {@code BlogUser}s
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	List<BlogUser> getAllUsers() throws DAOException;
	
	/**
	 * Saves the given {@link BlogUser}.
	 * 
	 * @param  user         user to be saved
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	void addUser(BlogUser user) throws DAOException;
	
	/**
	 * Returns a list of {@link BlogComment}s for entry with the given {@code id}.
	 * 
	 * @param  id           {@code id} of entry
	 * @return              a list of {@code BlogComment}s for entry with the 
	 *                      given {@code id}
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	List<BlogComment> getCommentsForEntry(Long id) throws DAOException;
	
	/**
	 * Saves the given {@link BlogComment}.
	 * 
	 * @param  comment      comment to be saved
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	void addComment(BlogComment comment) throws DAOException;
	
}
