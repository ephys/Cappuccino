package paoo.cappuccino.util.exception;

/**
 * Base exception for problems occurring in one of the application layers
 *
 * @author Guylian Cox
 */
public abstract class CappuccinoException extends RuntimeException {
  public CappuccinoException(final String message) {
    super(message);
  }

  public CappuccinoException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
