package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Implementation of {@link ServletContextListener} which is responsible for
 * initializing database connection pool when this web-application is started
 * and destroying it when this web-application ends.
 * 
 * @author Matija Frandolić
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties dbsettings = new Properties();
		try {
			String dbsettingsPath = sce.getServletContext().getRealPath(
				"/WEB-INF/dbsettings.properties"
			);
			dbsettings.load(Files.newInputStream(Paths.get(dbsettingsPath)));
		} catch (IOException e) {
			throw new RuntimeException(
				"Error occurred while loading database settings.", e
			);
		}
		
		List<String> expectedProperties = Arrays.asList(
			"host", "port", "name", "user", "password"
		);
		if (!dbsettings.stringPropertyNames().containsAll(expectedProperties)) {
			throw new RuntimeException(
				"Some properties are missing in dbsettings.properties file."
			);
		}
		
		String host = dbsettings.getProperty("host");
		String port = dbsettings.getProperty("port");
		String name = dbsettings.getProperty("name");
		String user = dbsettings.getProperty("user");
		String password = dbsettings.getProperty("password");
		String connectionURL = String.format("jdbc:derby://%s:%s/%s", host, port, name);
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e) {
			throw new RuntimeException("Error occurred while initializing pool.", e);
		}
		
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(user);
		cpds.setPassword(password);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		try {
			verifyTables(cpds);
		} catch (SQLException e) {
			throw new RuntimeException("Database access error occurred.", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		ComboPooledDataSource cpds = 
			(ComboPooledDataSource) context.getAttribute("hr.fer.zemris.dbpool");
		
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				throw new RuntimeException("Database access error occurred.", e);
			}
		}
	}
	
	/**
	 * Verifies that tables "Polls" and "PollOptions" exist in the database by
	 * creating them if they do not already exist. If these tables are created
	 * or found empty, they are populated with sample poll data so that in the 
	 * end there are at least two initial polls.
	 * 
	 * @param  ds           {@link DataSource} object to the database 
	 * @throws SQLException if a database access error occurs
	 */
	private static void verifyTables(DataSource ds) throws SQLException {
		String createPollsSQL = "CREATE TABLE Polls"
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "title VARCHAR(150) NOT NULL,"
			+ "message CLOB(2048) NOT NULL)";
		
		String createPollOptionsSQL = "CREATE TABLE PollOptions"
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "optionTitle VARCHAR(100) NOT NULL,"
			+ "optionLink VARCHAR(150) NOT NULL,"
			+ "pollID BIGINT,"
			+ "votesCount BIGINT,"
			+ "FOREIGN KEY (pollID) REFERENCES Polls(id))";
		
		try (Connection con = ds.getConnection()) {
			executeIfTableNotExists(con, "Polls", createPollsSQL);
			executeIfTableNotExists(con, "PollOptions", createPollOptionsSQL);	

			String countPollsSQL = "SELECT COUNT(*) FROM Polls";
			long numberOfPolls;
			
			try (
				PreparedStatement pst = con.prepareStatement(countPollsSQL);
				ResultSet rs = pst.executeQuery()
			) {
				rs.next();
				numberOfPolls = rs.getLong(1);
			}
			
			if (numberOfPolls == 0) {
				addFavoriteBandPoll(con);
				addFavoriteColorPoll(con);
			}
			
			if (numberOfPolls == 1) {
				addFavoriteBandPoll(con);
			}
		}
	}
	
	/**
	 * Executes the given SQL statement if table with the given name does not
	 * exist in the database.
	 * 
	 * @param  con          connection with a database
	 * @param  name         name of the table
	 * @param  statement    statement to be executed if table with the given name
	 *                      does not exist
	 * @throws SQLException if a database access error occurs
	 */
	private static void executeIfTableNotExists(Connection con, String name, 
			String statement) throws SQLException {
		
		DatabaseMetaData metaData = con.getMetaData();
		
		try (
			PreparedStatement pst = con.prepareStatement(statement);
			ResultSet rs = metaData.getTables(null, null, name.toUpperCase(), null)
		) {
			if (!rs.next()) {
				pst.execute();
			}
		}
	}
	
	/**
	 * Inserts the given {@link Poll} object into "Polls" table in the database 
	 * and returns generated ID for that record.
	 * 
	 * @param  con          connection with a database
	 * @param  poll         {@code Poll} object which is inserted into "Polls" table
	 * @return              generated ID for the inserted record
	 * @throws SQLException if a database access error occurs
	 */
	private static long insertPoll(Connection con, Poll poll) throws SQLException {
		String insertSQL = "INSERT INTO Polls (title, message) VALUES (?, ?)";
		
		try (
			PreparedStatement pst = 
			con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)
		) {
			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());
			pst.execute();
			
			try (ResultSet rs = pst.getGeneratedKeys()) {
				rs.next();
				return rs.getLong(1);
			}
		}
	}
	
	/**
	 * Inserts the given {@link PollOption} objects into "PollOptions" table in 
	 * the database.
	 * 
	 * @param  con          connection with a database
	 * @param  pollOptions  {@code PollOption} objects which are inserted into 
	 *                      "PollOption" table
	 * @throws SQLException if a database access error occurs
	 */
	private static void insertPollOptions(Connection con, PollOption ... pollOptions) 
			throws SQLException {
		
		String insertSQL = "INSERT INTO PollOptions"
			+ "(optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)";
		
		try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
			for (PollOption pollOption : pollOptions) {
				pst.setString(1, pollOption.getOptionTitle());
				pst.setString(2, pollOption.getOptionLink());
				pst.setLong(3, pollOption.getPollID());
				pst.setLong(4, pollOption.getVotesCount());
				pst.addBatch();
			}
			
			pst.executeBatch();
		}
	}
	
	/**
	 * Adds sample poll about favorite band to the database.
	 * 
	 * @param  con          connection with a database
	 * @throws SQLException if a database access error occurs
	 */
	private static void addFavoriteBandPoll(Connection con) throws SQLException {
		Poll favoriteBandPoll = new Poll(
			"Glasanje za omiljeni bend:",
			"Od sljedećih bendova, koji Vam je bend najdraži?" 
				+ " Kliknite na link kako biste glasali!"
		);

		long pollID = insertPoll(con, favoriteBandPoll);
		
		PollOption[] favoriteBandPollOptions = {
			new PollOption("The Beatles", 
				"https://www.youtube.com/watch?v=z9ypq6_5bsg", pollID, 0
			),
			new PollOption("The Platters", 
				"https://www.youtube.com/watch?v=H2di83WAOhU", pollID, 0
			),
			new PollOption("The Beach Boys", 
				"https://www.youtube.com/watch?v=2s4slliAtQU", pollID, 0
			),
			new PollOption("The Four Seasons", 
				"https://www.youtube.com/watch?v=y8yvnqHmFds", pollID, 0
			),
			new PollOption("The Marcels", 
				"https://www.youtube.com/watch?v=qoi3TH59ZEs", pollID, 0
			),
			new PollOption("The Everly Brothers", 
				"https://www.youtube.com/watch?v=tbU3zdAgiX8", pollID, 0
			),
			new PollOption("The Mamas And The Papas", 
				"https://www.youtube.com/watch?v=N-aK6JnyFmk", pollID, 0
			)
		};
		
		insertPollOptions(con, favoriteBandPollOptions);
	}
	
	/**
	 * Adds sample poll about favorite color to the database.
	 * 
	 * @param  con          connection with a database
	 * @throws SQLException if a database access error occurs
	 */
	private static void addFavoriteColorPoll(Connection con) throws SQLException {
		Poll favoriteColorPoll = new Poll(
			"Glasanje za omiljenu boju:",
			"Od sljedećih boja, koja Vam je boja najdraža?" 
				+ " Kliknite na link kako biste glasali!"
		);

		long pollID = insertPoll(con, favoriteColorPoll);
		
		PollOption[] favoriteColorPollOptions = {
			new PollOption("crvena", "https://i.imgur.com/txpmJmB.png", pollID, 0),
			new PollOption("narančasta", "https://i.imgur.com/9yILi61.png", pollID, 0),
			new PollOption("žuta", "https://i.imgur.com/Uv4mpqF.png", pollID, 0),
			new PollOption("zelena", "https://i.imgur.com/RuopxmJ.png", pollID, 0),
			new PollOption("plava", "https://i.imgur.com/RdduwZo.png", pollID, 0),
			new PollOption("ljubičasta", "https://i.imgur.com/W6qrpr3.png", pollID, 0)
		};
		
		insertPollOptions(con, favoriteColorPollOptions);
	}

}
