package paoo.cappuccino.dal.dao;

import java.util.List;

import paoo.cappuccino.business.dto.IBusinessDayDto;

/**
 * Data access object used to fetch or persist business days.
 */
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
   * Fetches all the business day.
   *
   * @return The list of persisted business days.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IBusinessDayDto> fetchAll();

  /**
   * Fetches the business days which no companies have been invited to yet.
   *
   * @return The list of matching business days.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IBusinessDayDto> fetchInvitationlessDays();

  /**
   * Fetches the business day that occurred during an academic year.
   *
   * @param year The first year of the academic year of the business day (ex; in "2012-2013" it
   *             would be 2012).
   * @return The business day or null if none was found.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IBusinessDayDto fetchBusinessDaysByDate(int year);

  /**
   * Fetches the business day by its identifier.
   *
   * @param id The identifier of a business day.
   * @return The business day matching the id or null if none was found.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IBusinessDayDto fetchBusinessDayById(int id);
}
