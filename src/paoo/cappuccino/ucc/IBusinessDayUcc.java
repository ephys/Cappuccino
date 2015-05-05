package paoo.cappuccino.ucc;

import java.time.LocalDateTime;
import java.util.List;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Use case controller containing methods relative to a Business Day as an entity.
 *
 * @author Laurent
 */
public interface IBusinessDayUcc {

  /**
   * Creates a new business day and persists it.
   *
   * @param evenDate The date of the event.
   * @return The new Business Day DTO.
   * @throws java.lang.IllegalArgumentException There is already a business day on that academic
   *         year, or the date is null.
   */
  IBusinessDayDto create(LocalDateTime evenDate);

  /**
   * Adds a list of invited companies to the business day participation list.
   *
   * @param companies The list of companies to add.
   * @param businessDay The business day the list must be added to.
   */
  void addInvitedCompanies(ICompanyDto[] companies,
      IBusinessDayDto businessDay);

  /**
   * Adds a list of invited contacts to the business day participation list.
   *
   * @param contacts The list of contacts to add.
   * @param businessDay The business day the list must be added to.
   * @param company The company of the contacts
   */
  void setInvitedContacts(List<Integer> contacts, IBusinessDayDto businessDay, ICompanyDto company);

  /**
   * Changes the state of a participation.
   *
   * @param participation The participation that needs its state changed.
   * @param state The new state of the participation.
   * @return true: the change was successful.
   */
  boolean changeState(IParticipationDto participation,
      IParticipationDto.State state);

  /**
   * If the participation isn't cancelled, it will set it as if.
   *
   * @param participation The participation which need to be cancelled.
   * @return True if cancelled and false if it was not possible.
   */
  boolean cancelParticipation(IParticipationDto participation);

  /**
   * Returns all the business day that do not have any participation yet.
   */
  List<IBusinessDayDto> getInvitationlessDays();

  /**
   * Returns the table of every registered business days.
   */
  List<IBusinessDayDto> getBusinessDays();

  /**
   * Returns the business day matching a given id.
   *
   * @param id the business day id.
   */
  IBusinessDayDto getBusinessDay(int id);

  /**
   * Returns the list of companies invited to a given business day,
   * no matter what the state of the participation if.
   *
   * @param businessDayId The identifier of the business day.
   */
  List<IParticipationDto> getParticipations(int businessDayId);

  /**
   * Returns the list of invited contacts for a given company and business day.
   *
   * @param company The company the contacts are working for.
   * @param businessDay The business day the contacts are attending.
   */
  List<IAttendanceDto> getAttendancesForParticipation(int businessDay,
                                                      int company);
}
