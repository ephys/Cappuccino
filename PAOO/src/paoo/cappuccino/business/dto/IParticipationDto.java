package paoo.cappuccino.business.dto;

/**
 * Data transfer object for the Participation entity.
 * 
 * @author Nicolas Fischer
 */
public interface IParticipationDto {

  /**
   * Returns whether or not the participation is cancelled.
   */
  boolean isCancelled();

  /**
   * Returns the state of the participation.
   */
  State getState();

  /**
   * Returns the business day of the participation.
   */
  IBusinessDayDto getBusinessDay();

  /**
   * Returns the company assisting on the participation.
   */
  ICompanyDto getCompany();

  /**
   * Returns the group of contacts assisting the event.
   */
  IContactDto[] getContacts();

  /**
   * Defines the list of states a participation can take.
   */
  public static enum State {
    INVITED, CONFIRMED, DECLINED, BILLED, PAID
  }
}
