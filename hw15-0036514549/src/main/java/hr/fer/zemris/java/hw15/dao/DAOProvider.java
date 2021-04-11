package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.jpa.JPADAOImpl;

/**
 * Singleton class which remembers which implementation of {@link DAO} is used
 * in the application (here it is set to {@link JPADAOImpl}).
 */
public class DAOProvider {

	/**
	 * Implementation of {@link DAO} to be used in the application.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns {@link DAO} object which is kept in this class.
	 * 
	 * @return {@code DAO} object which is kept in this class
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}
