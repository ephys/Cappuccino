package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.util.ParticipationUtils;

/**
 * Interface containing business methods relative to the participation entity.
 *
 * @author Nicolas Fischer
 */
public interface IParticipation extends IParticipationDto, IVersionedEntity {

  /**
   * Changes the state of the participation. See {@link ParticipationUtils#getFollowingStates(IParticipationDto.State)}
   * to know which states are valid.
   *
   * @param state The new state.
   * @throws java.lang.IllegalStateException Cannot change the state to the given one.
   */
  void setState(State state);

  /**
   * Cancels the participation.
   *
   * @throws java.lang.IllegalStateException The participation cannot be cancelled (yet) or is
   *                                         already in the given state.
   */
  void setCancelled(boolean cancelled);
}
