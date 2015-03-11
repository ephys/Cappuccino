package paoo.cappuccino.util.exception;

/**
 * Base exception for problems occurring in one of the application layers.
 *
 * @author Guylian Cox
 */
public abstract class CappuccinoException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 4998709235189878639L;

  public CappuccinoException(final String message) {
    super(message);
  }

  public CappuccinoException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
