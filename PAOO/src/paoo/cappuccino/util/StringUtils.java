package paoo.cappuccino.util;

/**
 * Utility methods callable for strings
 *
 * @author Guylian Cox
 */
public class StringUtils {

  private static final String EMAIL_REGEX = "";

  /**
   * Verifies a string is following a valid email format.
   *
   * @param str The string to check.
   * @return true if the string is an email, false otherwise.
   */
  public static boolean isEmail(String str) {
    // are we allowed to use libraries ? Because we shouldn't handle this ourselves
    return str.matches(EMAIL_REGEX);
  }

  /**
   * Checks if a string contains non-space characters
   *
   * @param str The string to check.
   * @return true if the string contains non-space characters, false otherwise.
   */
  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }
}
