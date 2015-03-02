package paoo.cappuccino.dal.impl;

import java.sql.SQLException;

import paoo.cappuccino.dal.IDalSProvider;

/**
 * 
 * @author Bavay Kevin
 *
 */
public class MonoThread implements IDalSProvider {

  private static SqlService service = null;

  private MonoThread() {
    super();
  }

  /**
   * Create or return a SqlService singleton
   * 
   * @return SqlService
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public final static SqlService getSqlService() throws ClassNotFoundException, SQLException {
    if (MonoThread.service == null) {
      synchronized (MonoThread.class) {
        if (MonoThread.service == null) {
          MonoThread.service = new SqlService();
        }
      }
    }
    return MonoThread.service;
  }

}
