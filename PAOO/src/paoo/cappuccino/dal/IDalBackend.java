package paoo.cappuccino.dal;

import java.sql.PreparedStatement;

import paoo.cappuccino.core.injector.Singleton;

/**
 * Interface containing methods used by DAOs to execute queries.
 *
 * @author Kevin Bavay
 */
@Singleton
public interface IDalBackend {

  /**
   * Creates a new PrepardedStatement for a query.
   *
   * @param query the query to prepare.
   */
  public PreparedStatement fetchPrepardedStatement(String query);
}
