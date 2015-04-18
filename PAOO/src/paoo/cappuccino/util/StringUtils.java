package paoo.cappuccino.util;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Utility methods callable for strings.
 *
 * @author Guylian Cox
 */
public class StringUtils {

  private static final Pattern NUM_REGEX = Pattern.compile("^[a-zA-Z]*$");
  private static final Pattern ALPHA_REGEX = Pattern.compile("^[a-zA-Z]*$");
  private static final Pattern NONEMPTY_REGEX = Pattern.compile("\\S+");
  private static final Pattern EMAIL_REGEX = Pattern.compile(
      "^([a-zA-Z0-9][+a-zA-Z0-9_.-]*)+@([a-zA-Z0-9][a-zA-Z0-9_.-]*)+\\.[a-zA-Z]{2,3}$");

  private static final Random random = new Random();

  /**
   * Verifies if a string contains only alphabetical letters.
   *
   * @param str The string to check.
   * @return true if the string contains only letters, false otherwise.
   */
  public static boolean isAlphaString(String str) {
    return ALPHA_REGEX.matcher(str).matches();
  }

  /**
   * Verifies if a string contains only numbers
   *
   * @param str The string to check.
   * @return true if the string contains only numbers, false otherwise.
   */
  public static boolean isNumeric(String str) {
    return NUM_REGEX.matcher(str).matches();
  }

  /**
   * Verifies if a string is following a valid email format.
   *
   * @param str The string to check.
   * @return true if the string is an email, false otherwise.
   */
  public static boolean isEmail(String str) {
    // TODO (post-school) We shouldn't handle this ourselves, use a lib.
    return EMAIL_REGEX.matcher(str).matches();
  }

  /**
   * Verifies if a string is following a valid password format according to the application
   * specifications.
   *
   * @param str The string to check.
   * @return true if the string is a password, false otherwise.
   */
  public static boolean isValidPassword(char[] str) {
    // TODO (post-school) note: implement something to ensure a high entropy ?
    // http://en.wikipedia.org/wiki/Password_strength
    return str.length >= 6;
  }

  /**
   * Checks if a string contains non-space characters.
   *
   * @param str The string to check.
   * @return true if the string contains non-space characters, false otherwise.
   */
  public static boolean isEmpty(String str) {
    // TODO (post-school) note: this does not match unicode whitespaces, consider using a lib.
    return str == null || !NONEMPTY_REGEX.matcher(str).matches();

    //return str == null || str.trim().length() == 0;
  }

  /**
   * Converts a string containing hexadecimal numbers to a byte array.
   *
   * @param hexStr The string to convert
   * @return the byte array version
   */
  public static byte[] strToBytes(String hexStr) {
    byte[] table = new byte[hexStr.length() >> 1];

    for (int i = hexStr.length() - 1; i > 0; i -= 2) {
      int hexValue = Integer.parseInt(hexStr.substring(i - 1, i + 1), 16);

      table[i >> 1] = (byte) hexValue;
    }

    return table;
  }

  /**
   * Converts an array of bytes to an string of hexadecimal numbers.
   *
   * @param hexTab The table to stringify
   * @return A string representation of the table
   */
  public static String bytesToString(byte[] hexTab) {
    StringBuilder str = new StringBuilder(hexTab.length * 2);

    for (byte b : hexTab) {
      String hexRepresentation = Integer.toHexString(b & 0xFF);

      if (hexRepresentation.length() == 1) {
        str.append('0');
      }

      str.append(hexRepresentation);
    }

    return str.toString();
  }

  /**
   * Replaces the contents of a char array by random characters. Avoids the release of sensitive
   * data via memory dumps.
   *
   * @param str the data to clear.
   */
  public static void clearString(char[] str) {
    for (int i = 0; i < str.length; i++) {
      str[i] = (char) random.nextInt();
    }
  }
}
