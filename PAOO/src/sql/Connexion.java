package sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Bavay Kevin
 *
 */
public interface Connexion {

	/**
	 * ferme la connection en param
	 * 
	 * @param connexion
	 * @throws SQLException
	 */
	public void deconnexionDb(Connection connexion) throws SQLException;

	/**
	 * 
	 * @return nouvelle connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection connexion() throws SQLException, ClassNotFoundException;

}
