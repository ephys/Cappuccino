package paoo.cappuccino.dal.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import paoo.cappuccino.dal.IConnectionProvider;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;

/**
 * 
 * @author Bavay Kevin
 *
 */
public class SqlService implements IDalService, IDalBackend {

  private boolean transactionPending;
  private IConnectionProvider connectionProvider = new ConnectionProvider();

  private Connection conn;

  /**
   * Create a new Connection
   * 
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public SqlService() throws ClassNotFoundException, SQLException {
    conn = connectionProvider.connectDB();
  }

  @Override
  public synchronized boolean startTransaction() throws ClassNotFoundException, SQLException {
    if (transactionPending) {
      return false;
    }
    conn.setAutoCommit(false);
    transactionPending = true;
    return true;
  }

  @Override
  public boolean commit() throws SQLException {
    if (!transactionPending) {
      return false;
    }
    conn.commit();
    transactionPending = false;
    return true;
  }

  @Override
  public boolean rollBack() throws SQLException {
    if (!transactionPending) {
      return false;
    }
    conn.rollback();
    transactionPending = false;
    return true;
  }

  @Override
  public PreparedStatement getPrepardedStatement(String query) throws SQLException {
    if (!transactionPending) {
      return null;
    }
    return conn.prepareStatement(query);
  }
}
