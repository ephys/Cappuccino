package paoo.cappuccino.dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Bavay Kevin
 *
 */
public interface IDalBackend {

	/**
	 * Create a PrepardedStatement with the query in param.
	 * 
	 * @param query
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement getPrepardedStatement(String query) throws SQLException;

}
