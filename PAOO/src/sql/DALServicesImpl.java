package sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

/**
 * 
 * @author Bavay Kevin
 *
 */
public class DALServicesImpl implements DALServices {

	Connexion connexion = new ConnexionImpl();
	Hashtable<Statement, Connection> connexions = new Hashtable<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.DALServices#getPrepardedStatement()
	 */
	public Statement getPrepardedStatement() throws ClassNotFoundException, SQLException {
		Connection con = connexion.connexion();
		Statement stat = con.createStatement();
		connexions.put(stat, con);
		return stat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.DALServices#closeStatement(java.sql.Statement)
	 */
	public void closeStatement(Statement statement) throws SQLException {
		statement.close();
		connexions.get(statement).close();
		connexions.remove(statement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.DALServices#startTransaction()
	 */
	public void startTransaction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.DALServices#commit()
	 */
	public void commit() throws ClassNotFoundException, SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sql.DALServices#rollback()
	 */
	public void rollback() {

	}

}
