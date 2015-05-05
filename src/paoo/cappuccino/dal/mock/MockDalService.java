package paoo.cappuccino.dal.mock;

import java.sql.PreparedStatement;

import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;

/**
 * Mock implementation of the DAL service & backend.
 */
class MockDalService implements IDalBackend, IDalService {

  @Override
  public PreparedStatement fetchPreparedStatement(String query) {
    return null;
  }

  @Override
  public void startTransaction() {
    // do nothing
  }

  @Override
  public void commit() {
    // do nothing
  }

  @Override
  public void rollback() {
    // do nothing
  }
}
