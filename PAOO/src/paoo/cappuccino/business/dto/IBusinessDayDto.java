package paoo.cappuccino.business.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for the Business day entity.
 *
 * @author Nicolas Fischer
 */
public interface IBusinessDayDto {

  /**
   * Gets the date on which the event will occur.
   */
  LocalDateTime getEventDate();

  /**
   * Gets the date on which the event was registered.
   */
  LocalDateTime getCreationDate();

  /**
   * Gets the list of participations.
   */
  IParticipationDto[] getParticipations();
}
