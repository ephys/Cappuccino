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

  IParticipationDto[] fetchParticipationsByCompany(int companyId);
}
