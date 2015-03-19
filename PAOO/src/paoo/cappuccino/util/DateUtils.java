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
   * Returns the Belgian academic year of a LocalDate.
   *
   * @param date The date from which the academic year is to be extracted.
   * @return the two years of the academic year separated by a hyphen ("year1-year2").
   */
  public static String getAcademicYear(LocalDateTime date) {
    int year = date.getYear();

    if (date.getMonth().compareTo(Month.JULY) < 0) {
      year--;
    }

    return year + "-" + (year + 1);
  }
}
