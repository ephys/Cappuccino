package paoo.cappuccino.util;

/**
 * Utility methods callable for strings.
 *
 * @author Guylian Cox
 */
public class StringUtils {

  //private static final String PHONE_NUM_REGEX =
  //    "^(([0-9]{4})[ -_/.]((([0-9]{2}){3})|([0-9]{3}[ -_/.]?[0-9]{3})|([0-9][0-9][ -_/.]?[0-9][0-9][ -_/.]?[0-9][0-9])))||([0-9]{10})$";
  private static final String EMAIL_REGEX =
      "^([a-zA-Z0-9][+a-zA-Z0-9_.-]*)+@([a-zA-Z0-9][a-zA-Z0-9_.-]*)+\\.[a-zA-Z]{2,3}$";

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
   * Checks if a string contains non-space characters.
   *
   * @param str The string to check.
   * @return true if the string contains non-space characters, false otherwise.
   */
  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
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
}
