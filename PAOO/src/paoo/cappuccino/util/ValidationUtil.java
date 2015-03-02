package paoo.cappuccino.util;

/**
 * Utility methods for parameter validation
 *
 * @author Guylian Cox
 */
public class ValidationUtil {
  public void ensureNotNull(Object o, String parameterName) {
    if (o == null)
      throw new NullPointerException("'" + parameterName
          + "' parameter received an invalid value, it cannot be null");
  }
}
