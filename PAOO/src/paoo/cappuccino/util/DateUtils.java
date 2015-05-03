package paoo.cappuccino.util;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Utility methods for LocalDateTime(s).
 *
 * @author Guylian Cox
 */
public class DateUtils {

  /**
   * Returns the first year of a Belgian academic year. (for instance, the academic year "2014-2015"
   * would be represented by the integer 2014.
   *
   * @param date The date from which the academic year is to be extracted.
   * @return the first year of the academic year.
   */
  public static int getAcademicYear(LocalDateTime date) {
    int year = date.getYear();

    if (date.getMonth().compareTo(Month.JULY) < 0) {
      year--;
    }

    return year;
  }

  public static int getAcademicYear() {
    return getAcademicYear(LocalDateTime.now());
  }
}
