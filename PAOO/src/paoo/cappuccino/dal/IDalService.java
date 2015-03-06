package paoo.cappuccino.dal;

import paoo.cappuccino.core.injector.Singleton;

/**
 * Interface responsible for handling transactions.
 *
 * @author Kevin Bavay
 */
@Singleton(redirectTo = IDalBackend.class)
public interface IDalService {
  /**
   * Starts a transaction.
   *
   * @throws java.lang.IllegalStateException This thread already has a transaction is already in
   *                                         progress.
   */
  public void startTransaction();

  /**
   * Closes the transaction by committing the changes.
   *
   * @throws java.lang.IllegalStateException This thread doesn't have a transaction running.
   */
  public void commit();

  /**
   * Closes the transaction by rollbacking the changes.
   *
   * @throws java.lang.IllegalStateException This thread doesn't have a transaction running.
   */
  public void rollback();

}
