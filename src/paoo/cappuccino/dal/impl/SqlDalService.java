package paoo.cappuccino.dal.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.util.exception.FatalException;

/**
 * Data access layer services for an SQL connection.
 *
 * @author Bavay Kevin
 */
class SqlDalService implements IDalService, IDalBackend {

  private final String host;
  private final String user;
  private final String password;

  @Inject
  public SqlDalService(IAppConfig config) {
    host = config.getString("db_host");
    user = config.getString("db_username");
    password = config.getString("db_password");

    try {
      Class.forName(config.getString("jdbc.driver"));
    } catch (Exception e) {
      throw new FatalException("Could not fetch the JDBC driver.", e);
    }

    DriverManager.setLoginTimeout(5);
  }

  private ThreadLocal<Connection> connections = new ThreadLocal<>();

  private Connection getThreadConnection() {
    try {
      Connection connection = connections.get();
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(host + "?user=" + user + "&password=" + password);


        connections.set(connection);
        return connection;
      }

      return connection;
    } catch (SQLException e) {
      throw new FatalException("Could not fetch the thread connection", e);
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
      throw new FatalException("Could not start the transaction", e);
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
      throw new FatalException("Could not commit the transaction", e);
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
      throw new FatalException("Could not rollback the transaction", e);
    }
  }

  @Override
  public PreparedStatement fetchPreparedStatement(String query) {
    Connection sqlConnection = getThreadConnection();

    try {
      return sqlConnection.prepareStatement(query);
    } catch (SQLException e) {
      throw new FatalException("Could not create statement " + query, e);
    }
  }
}
