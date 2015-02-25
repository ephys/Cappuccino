package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implémente sql.Connexion
 * 
 * @author Bavay Kevin
 *
 */
public class ConnexionImpl implements Connexion {

	private String url = "jdbc:postgresql://localhost:5433/postgres";
	private String login = "postgres", password = "1234";

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.Connexion#deconnexionDb(java.sql.Connection)
	 */
	public void deconnexionDb(Connection connexion) throws SQLException {
		connexion.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.Connexion#connexion()
	 */
	public Connection connexion() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		String url = this.url + "?user=" + this.login + "&password=" + this.password;
		return DriverManager.getConnection(url);
	}

}
