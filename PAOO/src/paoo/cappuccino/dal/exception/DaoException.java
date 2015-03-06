package paoo.cappuccino.dal.exception;

public class DaoException extends DalException {

  public DaoException(String message) {
    super(message);
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
