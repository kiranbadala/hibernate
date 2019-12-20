package com.kiran.practice.hibernate.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kiran.practice.hibernate.hibernate.entity.Post;

@Component
public class PostDao {

	Logger logger = LoggerFactory.getLogger(PostDao.class);

	@Autowired
	EntityManagerFactory sf;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	private void createInitialData() {
		for (int counter = 0; counter <= 10000; counter++) {
			entityManager.merge(new Post(counter, "Details : " + counter, counter + 1));
			;
		}
	}

	private void createMoreData(int temp, int size) {
		for (int counter = temp; counter <= temp + size; counter++) {
			entityManager.merge(new Post(counter, "Details : " + counter, counter + 1));
		}

	}

	public void savePostWithOutBatchUsingJPA() {
		createInitialData();
		entityManager.flush();
	}

	public void savePostWithBatchUsingJPA() {
		entityManager.unwrap(Session.class).setJdbcBatchSize(500);
		createMoreData(1001, 1000);
		entityManager.flush();
	}

	public void updatePostWithNativeSqlWithOutBatch() {
		try (Connection connection = dataSource.getConnection();) {

			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			for (int counter = 0; counter <= 100; counter++) {
				statement
						.executeUpdate("UPDATE PUBLIC.POST SET VERSION=" + (counter + 1) + " WHERE POST_ID=" + counter);
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePostWithNativeSqlWithBatch(int batchSize) {
		try (Connection connection = dataSource.getConnection();) {

			connection.setAutoCommit(false);
			StringBuilder temp = new StringBuilder("");
			Statement statement = connection.createStatement();
			for (int counter = 0; counter <= 100; counter++) {
				statement.addBatch("UPDATE PUBLIC.POST SET VERSION=" + (counter + 1) + " WHERE POST_ID=" + counter);
				if (counter % batchSize == 0)
					temp.append(statement.executeBatch().length);
			}
			statement.executeBatch();
			// logger.info("Number of rows impacted : " + temp.toString());
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePostUsingJdbcPreparedStatementWithWithBatch(int batchSize) {

		try (Connection connection = dataSource.getConnection();) {

			connection.setAutoCommit(false);
			StringBuilder temp = new StringBuilder("");
			PreparedStatement statement = connection
					.prepareStatement("UPDATE PUBLIC.POST SET VERSION= ? WHERE POST_ID = ?");
			for (int counter = 0; counter <= 100; counter++) {
				statement.setInt(1, (counter + 1));
				statement.setInt(2, counter);
				statement.addBatch();
				if (counter % batchSize == 0)
					temp.append(statement.executeBatch().length);
			}
			statement.executeBatch();
			logger.info("Number of rows impacted : " + temp.toString());
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void fetchRecordsWithoutStatementCache() {
		try {
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "kiran");
			props.setProperty("preparedStatementCacheQueries", "0");
			props.setProperty("preparedStatementCacheSizeMiB", "0");
			Connection connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM PUBLIC.POST WHERE POST_ID = (SELECT MAX(VERSION) FROM PUBLIC.POST)");
			IntStream.range(1, 11).forEach((num) -> {
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				logger.debug("Result : ", resultSet.toString());

			});
			logger.info("Execution completed.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fetchRecordsWithStatementCache() {
		try {
			Properties props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "kiran");
			Connection connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props);
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM PUBLIC.POST WHERE POST_ID = (SELECT MAX(VERSION) FROM PUBLIC.POST)");
			IntStream.range(1, 11).forEach((num) -> {
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				logger.debug("Result : ", resultSet.toString());
			});
			logger.info("Execution completed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createConnectionsJdbc() {
		List<Connection> connections = new ArrayList<>();
		boolean flag = true;
		int connectionCount = 0;
		while (flag) {
			try {
				Connection connection = DriverManager.getConnection(
						"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", "postgres",
						"kiran");
				connections.add(connection);
				holdConnection(connection);
				logger.info("Count :" + connectionCount);
				connectionCount++;
			} catch (SQLException e) {
				logger.error("Exception : ", e);
			} finally {
				connections.stream().forEach((con) -> {
					try {
						con.close();
					} catch (SQLException e) {
						logger.error("Exception while closing conection : ", e);
					}
				});
			}
		}
		logger.info("No of connections created : " + connectionCount);

	}

	public void createConnections() {
		List<Connection> connections = new ArrayList<>();
		boolean flag = true;
		int connectionCount = 0;
		while (flag) {
			try {
				// DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log",
				// "postgres", "kiran");
				Connection connection = dataSource.getConnection();
				connections.add(connection);
				holdConnection(connection);
				logger.info("Count :" + connectionCount);
				connectionCount++;
			} catch (SQLException e) {
				logger.error("Exception : ", e);
			} finally {
				connections.stream().forEach((con) -> {
					try {
						con.close();
					} catch (SQLException e) {
						logger.error("Exception while closing conection : ", e);
					}
				});
			}
		}
		logger.info("No of connections created : " + connectionCount);
	}

	private void holdConnection(Connection connection) {
		try {
			connection.createStatement().execute("select * from public.post where post_id=1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fetchRecordsWithFetchSize(int size) {

		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "kiran");
		props.setProperty("preparedStatementCacheQueries", "0");
		props.setProperty("preparedStatementCacheSizeMiB", "0");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props)) {
			PreparedStatement statement = connection
					.prepareStatement("SELECT POST_ID,TITLE,VERSION FROM PUBLIC.POST ORDER BY POST_ID");
			statement.setFetchSize(size);// Set the fetch size here.
			ResultSet resultSet = null;
			try {
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String details = resultSet.getString(2);
				int version = resultSet.getInt(3);
				logger.debug("id : " + id + " title :" + details + " version :" + version);
			}
			logger.info("Execution completed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void fetchRowsWithMaxRows(int maxRows) {

		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "kiran");
		props.setProperty("preparedStatementCacheQueries", "0");
		props.setProperty("preparedStatementCacheSizeMiB", "0");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props)) {
			PreparedStatement statement = connection
					.prepareStatement("SELECT POST_ID,TITLE,VERSION FROM PUBLIC.POST ORDER BY POST_ID");
			statement.setMaxRows(maxRows);
			ResultSet resultSet = null;
			try {
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String details = resultSet.getString(2);
				int version = resultSet.getInt(3);
				logger.debug("id : " + id + " title :" + details + " version :" + version);
			}
			logger.info("Execution completed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void fetchAllRows() {
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "kiran");
		props.setProperty("preparedStatementCacheQueries", "0");
		props.setProperty("preparedStatementCacheSizeMiB", "0");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props)) {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection
					.prepareStatement("SELECT POST_ID,TITLE,VERSION FROM PUBLIC.POST ORDER BY POST_ID");
			ResultSet resultSet = null;
			try {
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String details = resultSet.getString(2);
				int version = resultSet.getInt(3);
				logger.debug("id : " + id + " title :" + details + " version :" + version);
			}
			logger.info("Execution completed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void fetchRowsWithLimit(int limit) {

		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "kiran");
		props.setProperty("preparedStatementCacheQueries", "0");
		props.setProperty("preparedStatementCacheSizeMiB", "0");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props)) {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection
					.prepareStatement("SELECT POST_ID,TITLE,VERSION FROM PUBLIC.POST ORDER BY POST_ID LIMIT ?");
			statement.setInt(1, limit);
			ResultSet resultSet = null;
			try {
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String details = resultSet.getString(2);
				int version = resultSet.getInt(3);
				logger.debug("id : " + id + " title :" + details + " version :" + version);
			}
			logger.info("Execution completed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void fetchTransactionIsolationLevel() {


		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "kiran");
		props.setProperty("preparedStatementCacheQueries", "0");
		props.setProperty("preparedStatementCacheSizeMiB", "0");

		try (Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/postgres?loggerLevel=TRACE&loggerFile=dbLog.log", props)) {
			connection.setAutoCommit(false);
			
			PreparedStatement statement = connection
					.prepareStatement("SELECT POST_ID,TITLE,VERSION FROM PUBLIC.POST ORDER BY POST_ID LIMIT ?");
			ResultSet resultSet = null;
			try {
				statement.setInt(1, 2);
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			logger.info("Connection.TRANSACTION_NONE : {}",Connection.TRANSACTION_NONE);
			logger.info("Connection.TRANSACTION_READ_COMMITTED : {}",Connection.TRANSACTION_READ_COMMITTED);
			logger.info("Connection.TRANSACTION_READ_UNCOMMITTED : {}",Connection.TRANSACTION_READ_UNCOMMITTED);
			logger.info("Connection.TRANSACTION_REPEATABLE_READ : {}",Connection.TRANSACTION_REPEATABLE_READ);
			logger.info("Connection.TRANSACTION_SERIALIZABLE : {}",Connection.TRANSACTION_SERIALIZABLE);
			logger.info("Transaction isolation level : "+connection.getMetaData().getDefaultTransactionIsolation());
			logger.info("Execution completed.",resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	
	}

}
