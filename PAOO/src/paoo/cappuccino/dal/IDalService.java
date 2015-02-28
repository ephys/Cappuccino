package paoo.cappuccino.dal;

import java.sql.SQLException;

/**
 * 
 * @author Bavay Kevin
 *         Je vais sans doute changer les boolean par des exceptions
 *
 */
public interface IDalService {

	/**
	 * Start a transaction
	 * 
	 * @return boolean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean startTransaction() throws ClassNotFoundException, SQLException;

	/**
	 * Start a commit
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean commit() throws SQLException;

	/**
	 * Execute a roolback
	 * 
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean rollBack() throws SQLException;

}
