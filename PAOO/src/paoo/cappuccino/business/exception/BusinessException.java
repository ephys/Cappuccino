package paoo.cappuccino.business.exception;

import paoo.cappuccino.util.exception.CappuccinoException;

/**
 * Base exception for problems occurring on the business layer of the application
 *
 * @author Nicolas Fischer
 */
public abstract class BusinessException extends CappuccinoException {
  public BusinessException(final String message) {
    super(message);
  }

  public BusinessException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
