package paoo.cappuccino.dal;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Bavay Kevin
 *
 */
public interface IConnectionProvider {

  /**
   * Close the Connection
   * 
   * @param con
   * @throws SQLException
   */
  public void disconnectDB(Connection con) throws SQLException;

  /**
   * Create un new Connection
   * 
   * @return Connection
   * @throws ClassNotFoundException If could not find the JDBC Driver
   * @throws SQLException If could not create a new Connection
   */
  public Connection connectDB() throws ClassNotFoundException, SQLException;

}
