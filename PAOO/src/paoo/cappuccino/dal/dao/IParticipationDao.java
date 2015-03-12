package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.IParticipationDto;

public interface IParticipationDao {

  IParticipationDto createParticipation(IParticipationDto participation);

  /**
   * Fetches the participations relative to a given business day.
   *
   * @param businessDay The day the participations are relative to.
   * @return The list of participation entities.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error.
   */
  /* TODO Eager loading: Automatically fetches the participations' company (with the same query). */
  IParticipationDto[] fetchParticipationsByDate(IBusinessDayDto businessDay);

  /* TODO Eager loading: Automatically fetches the participations' contacts and business days
     (with the same query). */
  IParticipationDto[] fetchParticipationsByCompany(IBusinessDayDto businessDay);
}
