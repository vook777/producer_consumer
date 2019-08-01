package codeinside.producer_consumer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcDao {

	public Connection getConnection() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(ConnectionPropertiesHolder.URL, ConnectionPropertiesHolder.USER,
					ConnectionPropertiesHolder.PASSWORD);
		} catch (SQLException e) {
			throw new DaoException("Cannot create connection");
		}
		return connection;
	}
}
