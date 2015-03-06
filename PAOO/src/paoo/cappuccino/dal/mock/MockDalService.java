package paoo.cappuccino.dal.mock;

import java.sql.PreparedStatement;

import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.IDalService;

public class MockDalService implements IDalBackend, IDalService {

  @Override
  public PreparedStatement fetchPrepardedStatement(String query) {
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
