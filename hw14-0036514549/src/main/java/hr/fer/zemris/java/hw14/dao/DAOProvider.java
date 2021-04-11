package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Singleton class which remembers which implementation of {@link DAO} is used
 * in the application (here it is set to {@link SQLDAO}).
 */
public class DAOProvider {

	/**
	 * Implementation of {@link DAO} to be used in the application.
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Returns {@link DAO} object which is kept in this class.
	 * 
	 * @return {@code DAO} object which is kept in this class
	 */
	public static DAO getDao() {
		return dao;
	}
	
}
