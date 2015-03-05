package paoo.cappuccino.dal.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import paoo.cappuccino.core.Config;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.exception.ConnectionException;
import paoo.cappuccino.util.exception.FatalException;

/**
 * @author Bavay Kevin
 */
class SqlDalService implements IDalService, IDalBackend {

  private static final String HOST = Config.getString("db_host");
  private static final String USER = Config.getString("db_username");
  private static final String PASSWORD = Config.getString("db_password");

  static {
    try {
      Class.forName(Config.getString("jdbc.driver"));
    } catch (Exception e) {
      throw new FatalException("Could not fetch the JDBC driver.", e);
    }
  }

  private HashMap<Long, Connection> connections = new HashMap<>(1);

  private Connection getThreadConnection() {
    final long threadId = Thread.currentThread().getId();

    try {
      Connection connection = connections.get(threadId);
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(
            HOST + "?user=" + USER + "&password=" + PASSWORD);

        connections.put(threadId, connection);
        return connection;
      }

      return connection;
    } catch (SQLException e) {
      throw new ConnectionException("Could not fetch the thread connection", e);
    }
  }

  @Override
  public void startTransaction() {
    Connection sqlConnection = getThreadConnection();

    try {
      if (!sqlConnection.getAutoCommit()) {
        throw new IllegalStateException("A transaction is already running in this thread.");
      }

      sqlConnection.setAutoCommit(false);
    } catch (SQLException e) {
      throw new ConnectionException("Could not start the transaction", e);
    }
  }

  @Override
  public void commit() {
    Connection sqlConnection = getThreadConnection();

    try {
      if (sqlConnection.getAutoCommit()) {
        throw new IllegalStateException("A transaction must be running before committing it.");
      }

      sqlConnection.commit();
      sqlConnection.setAutoCommit(true);
    } catch (SQLException e) {
      throw new ConnectionException("Could not commit the transaction", e);
    }
  }

  @Override
  public void rollback() {
    Connection sqlConnection = getThreadConnection();

    try {
      if (sqlConnection.getAutoCommit()) {
        throw new IllegalStateException("A transaction must be running before rollbacking it.");
      }

      sqlConnection.rollback();
      sqlConnection.setAutoCommit(true);
    } catch (SQLException e) {
      throw new ConnectionException("Could not rollback the transaction", e);
    }
  }

  @Override
  public PreparedStatement fetchPrepardedStatement(String query) {
    Connection sqlConnection = getThreadConnection();

    try {
      return sqlConnection.prepareStatement(query);
    } catch (SQLException e) {
      throw new ConnectionException("Could not create statement " + query, e);
    }
  }
}
