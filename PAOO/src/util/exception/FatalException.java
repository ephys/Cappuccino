package util.exception;

/**
 * This exception is a wrapper for non-runtime exceptions that should not have happened.
 * This exception is not to be caught by anything except the Global Exception Handler.
 *
 * @author Guylian Cox
 */
public class FatalException extends RuntimeException {
  public FatalException(String message, Throwable cause) {
    super(message, cause);
  }
}
