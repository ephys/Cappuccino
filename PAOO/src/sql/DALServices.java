package sql;

import java.sql.SQLException;
import java.sql.Statement;

public interface DALServices {

	/**
	 * Créé une nouvelle connection
	 * 
	 * @return statement
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Statement getPrepardedStatement() throws ClassNotFoundException, SQLException;

	/**
	 * Ferme la connection lié au statement passé en paramètre
	 * 
	 * @param statement
	 * 
	 * @throws SQLException
	 */
	public void closeStatement(Statement statement) throws SQLException;

	public void commit() throws ClassNotFoundException, SQLException;

	public void rollback();

	public void startTransaction();

}
