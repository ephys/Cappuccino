package paoo.cappuccino.dal;

import java.sql.PreparedStatement;

/**
 * Interface containing methods used by DAOs to execute queries.
 *
 * @author Kevin Bavay
 */
public interface IDalBackend {

  /**
   * Creates a new PrepardedStatement for a query.
   *
   * @param query the query to prepare.
   */
  public PreparedStatement fetchPrepardedStatement(String query);
}
