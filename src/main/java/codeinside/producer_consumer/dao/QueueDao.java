package codeinside.producer_consumer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueueDao extends JdbcDao {

	private final String STATUS_QUEUED = "queued";
	private final String STATUS_PROCESSED = "processed";

	public void createMessage(String message) throws ClassNotFoundException {
		String query = "insert into messages (status, message) VALUES (?, ?)";
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, STATUS_QUEUED);
			statement.setString(2, message);
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException("Cannot create message");
		}
	}

	public List<String> retrieveUnsentMessages() throws ClassNotFoundException {
		String query = "select * from messages where status = ?";
		List<String> queuedMessages = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, STATUS_QUEUED);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					String message = resultSet.getString("message");
					queuedMessages.add(message);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot retrieve messages");
		}
		updateMessages(queuedMessages);
		return queuedMessages;
	}

	public void deleteProcessedMessages() throws ClassNotFoundException {
		String query = "delete from messages where status = ?";
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, STATUS_PROCESSED);
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete processed messages");
		}
	}

	public void deleteAllMessages() throws ClassNotFoundException {
		String query = "delete from messages";
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException("Cannot delete all messages");
		}
	}

	public int countAllMessages() throws ClassNotFoundException {
		String query = "select count(*) from messages";
		int result = 0;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					result = resultSet.getInt("count");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot count all messages");
		}
		return result;
	}
	
	public int countProcessedMessages() throws ClassNotFoundException {
		String query = "select count(*) from messages where status = 'processed'";
		int result = 0;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					result = resultSet.getInt("count");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot count processed messages");
		}
		return result;
	}

	private void updateMessages(List<String> messages) {
		messages.stream().forEach(m -> {
			try {
				updateMessage(m);
			} catch (ClassNotFoundException e) {
				throw new DaoException("Cannot update messages");
			}
		});
	}

	private void updateMessage(String message) throws ClassNotFoundException {
		String query = "update messages set status = ? where message = ?";
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, STATUS_PROCESSED);
			statement.setString(2, message);
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException("Cannot update message");
		}
	}
}
