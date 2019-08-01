package codeinside.producer_consumer.dao;

import java.util.Properties;

public class ConnectionPropertiesHolder {

	private static final String PROPERTIES = "connection.properties";

	public static String URL;
	public static String USER;
	public static String PASSWORD;
	public static String DRIVER;

	private ConnectionPropertiesHolder() {}

	static {
		Properties properties = new Properties();
		try {
			properties.load(ConnectionPropertiesHolder.class.getClassLoader().getResourceAsStream(PROPERTIES));
		} catch (Exception e) {
			throw new DaoException("Cannot load properties");
		}
		URL = properties.getProperty("URL");
		USER = properties.getProperty("USER");
		PASSWORD = properties.getProperty("PASSWORD");
		DRIVER = properties.getProperty("DRIVER");
	}
}
