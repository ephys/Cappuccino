package paoo.cappuccino.util;

import static paoo.cappuccino.business.dto.IParticipationDto.State.BILLED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.CANCELLED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.CONFIRMED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.DECLINED;
import static paoo.cappuccino.business.dto.IParticipationDto.State.PAID;
import paoo.cappuccino.business.dto.IParticipationDto.State;

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
        return new State[] {BILLED, CANCELLED};
      case BILLED:
        return new State[] {PAID, CANCELLED};
      case PAID:
        return new State[] {CANCELLED};
      default:
        return new State[0];
    }
  }

}
