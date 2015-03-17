package paoo.cappuccino.ucc;

import java.time.LocalDateTime;

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
  public IBusinessDayDto create(LocalDateTime evenDate);

  /**
   * Adds a list of invited companies to the business day participation list.
   *
   * @param companies The list of companies to add.
   * @param businessDay The business day the list must be added to.
   * @return The complete list of invited companies.
   */
  public ICompanyDto[] addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay);

  /**
   * Changes the state of a participation.
   *
   * @param participation The participation that needs its state changed.
   * @param state The new state of the participation.
   * @return true: the change was successful.
   */
  public boolean changeState(IParticipationDto participation, IParticipationDto.State state);

  /**
   * Returns all the business day that haven't any participation yet.
   */
  public IBusinessDayDto[] getInvitationlessDays();

  /**
   * Returns the table of every registered business days.
   */
  public IBusinessDayDto[] getBusinessDays();

  /**
   * Returns the list of companies attending the business day matching a given identifier.
   *
   * @param businessDayId The identifier of the business day.
   */
  public ICompanyDto[] getAttendingCompanies(int businessDayId);
}
