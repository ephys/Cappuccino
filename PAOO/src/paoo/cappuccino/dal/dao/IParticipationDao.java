package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Data access object used to fetch or persist participations.
 */
public interface IParticipationDao {

  /**
   * Inserts a new participation in the database.
   *
   * @param participation The participation to insert.
   * @return The participation entity with its information updated from the database.
   * @throws java.lang.IllegalArgumentException            One of the fields failed to insert due to
   *                                                       constraint violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IParticipationDto createParticipation(IParticipationDto participation);

  /**
   * Updates a participation in the database.
   *
   * @param participation A participation entity.
   * @throws java.util.ConcurrentModificationException     The entity version did not match or the
   *                                                       entity has been deleted.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  void updateParticipation(IParticipationDto participation);

  /**
   * Fetches the participations relative to a given business day.
   *
   * @param businessDayId The day the participations are relative to.
   * @return The list of participation entities.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IParticipationDto[] fetchParticipationsByDate(int businessDayId);

  /**
   * Fetches the list of participations of a company.
   *
   * @param companyId The id of the company.
   * @return The list of participations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IParticipationDto[] fetchParticipationsByCompany(int companyId);
}
