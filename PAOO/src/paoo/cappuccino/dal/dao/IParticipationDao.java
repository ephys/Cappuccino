package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IParticipationDto;

public interface IParticipationDao {

  IParticipationDto createParticipation(IParticipationDto participation);

  /**
   * Fetches the participations relative to a given business day.
   *
   * @param businessDayId The day the participations are relative to.
   * @return The list of participation entities.
   * @throws paoo.cappuccino.dal.exception.FatalException Database connection error.
   */
  IParticipationDto[] fetchParticipationsByDate(int businessDayId);

  /**
   * Fetch all the participation of the company
   *
   * @param companyId The id of a company
   * @return All the participation or null if none was found
   * @throws paoo.cappuccino.dal.exception.FatalException Database connection error
   */
  IParticipationDto[] fetchParticipationsByCompany(int companyId);
}
