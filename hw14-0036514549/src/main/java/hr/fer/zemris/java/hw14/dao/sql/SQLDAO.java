package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link DAO} which uses SQL to obtain {@link Poll} and
 * {@link PollOption} objects from the database to which the connection is
 * acquired through {@link SQLConnectionProvider}.
 * 
 * @author Matija Frandolić
 */
public class SQLDAO implements DAO {

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		String selectSQL = "SELECT * FROM Polls WHERE id = ?";
		
		try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
			pst.setLong(1, id);
			
			try (ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurred while fetching poll.", e);
		}
		
		return poll;
	}
	
	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		String selectSQL = "SELECT * FROM Polls ORDER BY id";
		
		try (
			PreparedStatement pst = con.prepareStatement(selectSQL);
			ResultSet rs = pst.executeQuery()
		) {
			while(rs.next()) {
				Poll poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
				polls.add(poll);
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurred while fetching polls.", e);
		}
		
		return polls;
	}
	
	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption pollOption = null;
		Connection con = SQLConnectionProvider.getConnection();
		String selectSQL = "SELECT * FROM PollOptions WHERE id = ?";
		
		try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
			pst.setLong(1, id);
			
			try (ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					pollOption = new PollOption(
						rs.getLong(1),
						rs.getString(2),
						rs.getString(3),
						rs.getLong(4),
						rs.getLong(5)
					);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurred while fetching poll option.", e);
		}
		
		return pollOption;
	}

	@Override
	public List<PollOption> getPollOptions(long pollId) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		String selectSQL = "SELECT * FROM PollOptions WHERE pollID = ? ORDER BY id";
		
		try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
			pst.setLong(1, pollId);
			
			try (ResultSet rs = pst.executeQuery()) {
				while(rs.next()) {
					PollOption pollOption = new PollOption(
						rs.getLong(1),
						rs.getString(2),
						rs.getString(3),
						rs.getLong(4),
						rs.getLong(5)
					);
					pollOptions.add(pollOption);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurred while fetching poll options.", e);
		}
		
		return pollOptions;
	}
	
	@Override
	public void incrementVotesCount(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		String selectSQL = "UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = ?";
		
		try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
			pst.setLong(1, id);
			pst.execute();
		} catch (SQLException e) {
			throw new DAOException("Error occurred while incrementing votes count.", e);
		}
	}

}
