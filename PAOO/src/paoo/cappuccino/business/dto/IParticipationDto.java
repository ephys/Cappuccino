package paoo.cappuccino.business.dto;

/**
 * Data transfer object for the Participation entity.
 *
 * @author Nicolas Fischer
 */
public interface IParticipationDto {

  /**
   * Returns the state of the participation.
   */
  State getState();

  /**
   * Returns the id of the business day of the participation.
   */
  int getBusinessDay();

  /**
   * Returns the id of the company assisting on the participation.
   */
  int getCompany();

  /**
   * Gets the entity version.
   */
  int getVersion();

  /**
   * Returns whether or not the company cancelled its participation.
   */
  boolean isCancelled();

  /**
   * Defines the list of states a participation can take.
   */
  enum State {
    INVITED, CONFIRMED, DECLINED, BILLED, PAID
  }
}
