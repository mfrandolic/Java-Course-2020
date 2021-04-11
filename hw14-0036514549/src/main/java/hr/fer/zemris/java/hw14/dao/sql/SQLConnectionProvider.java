package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Class which remembers a connection to the database for the current thread.
 */
public class SQLConnectionProvider {

	/**
	 * Connection to the database for the current thread.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Sets the given connection to be an active connection to the database 
	 * for the current thread.
	 * 
	 * @param con connection to the database for the current thread
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Returns a connection to the database for the current thread.
	 * 
	 * @return connection to the database for the current thread
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}
