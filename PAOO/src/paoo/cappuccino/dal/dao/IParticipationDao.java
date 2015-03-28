package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IParticipationDto;

public interface IParticipationDao {

  /**
   * Inserts a new participation in the database.
   *
   * @param participation The participation to insert
   * @return The participation entity with its information updated from the database.
   * @throws java.lang.IllegalArgumentException            One of the fields failed to insert due to
   *                                                       constraint violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IParticipationDto createParticipation(IParticipationDto participation);

  /**
   * Fetches the participations relative to a given business day.
   *
   * @param businessDayId The day the participations are relative to.
   * @return The list of participation entities.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IParticipationDto[] fetchParticipationsByDate(int businessDayId);

  /**
   * Fetch all the participation of the company
   *
   * @param companyId The id of a company
   * @return All the participation or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error
   */
  IParticipationDto[] fetchParticipationsByCompany(int companyId);
}
