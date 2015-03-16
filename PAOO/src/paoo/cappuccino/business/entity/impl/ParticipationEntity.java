package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.util.ParticipationUtils;

/**
 * TODO, class javadoc.
 */
public class ParticipationEntity implements IParticipation {
  private State state;
  private int businessDay;
  private int company;
  private int version;

  /**
   * TODO.
   */
  public ParticipationEntity(int businessDayId, int companyId) {
    this(0, State.INVITED, businessDayId, companyId);
  }

  /**
   * TODO.
   */
  public ParticipationEntity(int version, State state, int businessDayId, int companyId) {
    this.state = state;
    this.businessDay = businessDayId;
    this.company = companyId;
    this.version = version;
  }

  @Override
  public State getState() {
    return this.state;
  }

  @Override
  public int getBusinessDay() {
    return this.businessDay;
  }

  @Override
  public int getCompany() {
    return this.company;
  }

  @Override
  public void setState(State newState) {
    State[] followingStates = ParticipationUtils.getFollowingStates(this.state);
    for (State otherState : followingStates) {
      if (otherState.equals(newState)) {
        this.state = newState;
        return;
      }
    }

    throw new IllegalArgumentException("Invalid state " + newState.name()
                                       + " cannot be transitioned directly from " + state.name());
  }

  @Override
  public int getVersion() {
    return this.version;
  }

  @Override
  public int incrementVersion() {
    return ++this.version;
  }

}
