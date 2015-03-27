package paoo.cappuccino.util;

import paoo.cappuccino.business.dto.IParticipationDto.State;

import static paoo.cappuccino.business.dto.IParticipationDto.State.BILLED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.CONFIRMED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.DECLINED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.PAID;

public class ParticipationUtils {


  /**
   * Returns the list of states a participation can be changed to.
   *
   * @param currentState The current participation state.
   */
  public static State[] getFollowingStates(State currentState) {
    switch (currentState) {
      case INVITED:
        return new State[] {CONFIRMED, DECLINED};
      case CONFIRMED:
        return new State[] {BILLED};
      case BILLED:
        return new State[] {PAID};
      case PAID:
      default:
        return new State[0];
    }
  }

}
