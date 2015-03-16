package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.util.ParticipationUtils;

public class ParticipationEntity implements IParticipation {
  private State state;
  private int businessDay;
  private int company;
  private int version;

  public ParticipationEntity(int businessDayId, int companyId) {
    this(0, State.INVITED, businessDayId, companyId);
  }

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
  public void setState(State state) {
    State[] fallowingState = ParticipationUtils.getFollowingStates(this.state);
    for (State state2 : fallowingState) {
      if (state2.equals(state))
        this.state = state;
    }
  }

  @Override
  public int getVersion() {
    return this.version;
  }

  @Override
  public int incrementVersion() {
    return this.version++;
  }

}
