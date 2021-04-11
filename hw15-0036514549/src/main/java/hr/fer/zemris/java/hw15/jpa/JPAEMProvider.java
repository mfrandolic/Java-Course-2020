package hr.fer.zemris.java.hw15.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Class which remembers {@link EntityManager} object for the current thread.
 */
public class JPAEMProvider {

	/**
	 * Entity manager for the current thread.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns {@link EntityManager} object for the current thread. New entity 
	 * manager is created if one does not already exist.
	 * 
	 * @return {@code EntityManager} object for the current thread
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		
		return em;
	}

	/**
	 * Closes {@link EntityManager} object for the current thread.
	 * 
	 * @throws DAOException if {@code DAO} access error has occurred
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		
		if (em == null) {
			return;
		}
		
		DAOException dex = null;
		
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		
		locals.remove();
		
		if (dex != null) {
			throw dex;
		}
	}
	
}
