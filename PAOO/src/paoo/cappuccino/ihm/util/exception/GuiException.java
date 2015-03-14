package paoo.cappuccino.ihm.util.exception;

import paoo.cappuccino.util.exception.CappuccinoException;

/**
 * Base exception for every problem caused by the ihm.
 */
public class GuiException extends CappuccinoException {

  public GuiException(String message) {
    super(message);
  }

  public GuiException(String message, Throwable cause) {
    super(message, cause);
  }
}
