package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Interface containing business methods relative to the participation entity.
 *
 * @author Nicolas Fischer
 */
public interface IParticipation extends IParticipationDto, IBaseEntity {

  void setState(State state);

  void setCancelled(boolean cancelled);
}
