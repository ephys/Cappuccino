package paoo.cappuccino.dal.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import paoo.cappuccino.config.Config;
import paoo.cappuccino.dal.IConnectionProvider;

/**
 * Create a new Connection to the database using url, login and password provide by the config file
 * 
 * @author Bavay Kevin
 *
 */
public class ConnectionProvider implements IConnectionProvider {

  // private String url = Config.getString("db.url");
  private String url = "jdbc:postgresql://localhost:5433/postgres";


  // private String login = Config.getString("db.login");
  // private String password = Config.getString("db.password");
  private String login = "postgres", password = "1234";

  @Override
  public Connection connectDB() throws ClassNotFoundException, SQLException {
    // System.out.println(Config.getString("org.postgresql.Driver"));
    Class.forName(Config.getString("jdbc.driver"));
    String url = this.url + "?user=" + this.login + "&password=" + this.password;
    return DriverManager.getConnection(url);
  }

  @Override
  public void disconnectDB(Connection con) throws SQLException {
    con.close();
  }

}
