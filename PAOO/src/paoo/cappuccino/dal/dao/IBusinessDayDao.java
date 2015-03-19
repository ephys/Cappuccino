package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IBusinessDayDto;

public interface IBusinessDayDao {

  /**
   * Inserts a new business day in the database.
   *
   * @param businessDay The business day to insert.
   * @return The business day entity with its information updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The is already a business day
   *                                                               during that academic year
   * @throws java.lang.IllegalArgumentException                    One of the fields failed to
   *                                                               insert due to constraint
   *                                                               violations.
   * @throws paoo.cappuccino.util.exception.FatalException         Database connection error.
   */
  IBusinessDayDto createBusinessDay(IBusinessDayDto businessDay);

  /**
   * Fetch all the business day
   *
   * @return All the business day found in the database or null if non was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IBusinessDayDto[] fetchAll();

  //TODO
  IBusinessDayDto[] fetchInvitationlessDays();

  /**
   * Fetch the BusinessDay corresponding at the date.
   *
   * @param year The first year of the academic year of the business day (ex; in "2012-2013" it
   *             would be 2012)
   * @return The business day or null if none was found.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error
   */
  IBusinessDayDto fetchBusinessDaysByDate(int year);
}
