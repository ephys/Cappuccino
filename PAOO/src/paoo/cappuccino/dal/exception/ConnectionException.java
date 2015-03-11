package paoo.cappuccino.dal.exception;

public class ConnectionException extends DalException {

  private static final long serialVersionUID = 8350099702833431327L;

  public ConnectionException(String message) {
    super(message);
  }

  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
