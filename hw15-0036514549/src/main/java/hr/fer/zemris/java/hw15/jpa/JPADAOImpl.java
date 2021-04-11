package hr.fer.zemris.java.hw15.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Implementation of {@link DAO} which uses JPA to obtain {@link BlogEntry}, 
 * {@link BlogEntry} and {@link BlogUser} objects.
 * 
 * @author Matija Frandolić
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public void addUser(BlogUser user) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(user);			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public BlogUser getUser(String nick) throws DAOException {
		try {
			BlogUser user = (BlogUser) JPAEMProvider.getEntityManager()
				.createQuery("SELECT user FROM BlogUser as user WHERE user.nick = :nick")
				.setParameter("nick", nick)
				.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("SELECT user FROM BlogUser as user")
				.getResultList();
			return users;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<BlogEntry> getEntriesForAuthor(String nick) throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogEntry> entries = (List<BlogEntry>) JPAEMProvider.getEntityManager()
				.createQuery("SELECT entry FROM BlogEntry as entry WHERE creator.nick = :nick")
				.setParameter("nick", nick)
				.getResultList();
			return entries;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void addBlogEntry(BlogEntry entry) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(entry);			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<BlogComment> getCommentsForEntry(Long id) throws DAOException {
		try {
			@SuppressWarnings("unchecked")
			List<BlogComment> comments = (List<BlogComment>) JPAEMProvider.getEntityManager()
				.createQuery("SELECT comment FROM BlogComment as comment WHERE blogEntry.id = :id")
				.setParameter("id", id)
				.getResultList();
			return comments;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void addComment(BlogComment comment) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(comment);			
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
