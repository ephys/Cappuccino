package paoo.cappuccino.dal.dao;

public interface IBusinessDaysDao {

  /**
   * Inserts a new business day in the database.
   *
   * @param businessDay The business day to insert
   * @return The business day entity with its informations updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The academic years is not
   *                                                               unique.
   * @throws java.lang.IllegalArgumentException                    One of the fields failed to
   *                                                               insert due to constraint
   *                                                               violations.
   * @throws paoo.cappuccino.dal.exception.ConnectionException     Database connection error
   */
  //IBusinessDayDto createBusinessDay(IBusinessDayDto businessDay);

  /**
   * Fetch the BusinessDay corresponding at the date.
   *
   * @param date The date of the business day
   * @return The business day or null if none was found.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //IBusinessDaysDto fetchBusinessDaysByDate(LocalDateTime date);

}
