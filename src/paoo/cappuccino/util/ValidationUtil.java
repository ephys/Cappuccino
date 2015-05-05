package paoo.cappuccino.util;

/**
 * Utility methods for parameter validation.
 *
 * @author Guylian Cox
 */
public class ValidationUtil {

  /**
   * Ensures an object is not null
   *
   * @param obj           The object to check.
   * @param parameterName The name of the variable / parameter checked.
   * @throws java.lang.NullPointerException The object is null
   */
  public static void ensureNotNull(Object obj, String parameterName) {
    if (obj == null) {
      throw new IllegalArgumentException("'" + parameterName
                                     + "' parameter received an invalid value, it cannot be null");
    }
  }

  /**
   * Ensure the given string matches password requirements
   *
   * @param password      The password to check
   * @param parameterName The name of the variable / parameter checked.
   * @throws java.lang.IllegalArgumentException The password does not match the requirements
   */
  public static void validatePassword(char[] password, String parameterName) {
    ensureNotNull(password, parameterName);

    if (!StringUtils.isValidPassword(password)) {
      throw new IllegalArgumentException(
          "Invalid parameter '" + parameterName + "', must be at least 6 characters long");
    }
  }

  /**
   * Ensure the given string is not empty (whitespaces do no count)
   *
   * @param str           The string to check
   * @param parameterName The name of the variable / parameter checked.
   * @throws java.lang.IllegalArgumentException The string is considered empty
   */
  public static void ensureFilled(String str, String parameterName) {
    ensureNotNull(str, parameterName);

    if (StringUtils.isEmpty(str)) {
      throw new IllegalArgumentException(
          "Invalid parameter '" + parameterName + "', must not be empty");
    }
  }

  /**
   * Ensure the given char table is not empty.
   *
   * @param str           The string to check
   * @param parameterName The name of the variable / parameter checked.
   * @throws java.lang.IllegalArgumentException The string is considered empty
   */
  public static void ensureFilled(char[] str, String parameterName) {
    ensureNotNull(str, parameterName);

    if (str.length == 0) {
      throw new IllegalArgumentException(
          "Invalid parameter '" + parameterName + "', must not be empty");
    }
  }
}
