package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.util.ParticipationUtils;

/**
 * Class implementing the IParticipation entity.
 *
 * @author Nicolas Fischer
 */
final class ParticipationEntity extends VersionedEntity implements IParticipation {

  private final int businessDay;
  private final int company;
  private State state;
  private boolean cancelled;

  public ParticipationEntity(int businessDayId, int companyId) {
    this(0, State.INVITED, false, businessDayId, companyId);
  }

  public ParticipationEntity(int version, State state, boolean cancelled, int businessDayId,
                             int companyId) {
    super(version);
    this.state = state;
    this.cancelled = cancelled;
    this.businessDay = businessDayId;
    this.company = companyId;
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

    throw new IllegalStateException("Invalid state " + newState.name()
                                    + " cannot be transitioned directly from " + state.name());
  }

  @Override
  public void setCancelled(boolean cancelled) {
    if (state == State.INVITED || state == State.DECLINED) {
      throw new IllegalStateException("Cannot cancel this participation, it hasn't been accepted.");
    }

    if (cancelled == this.cancelled) {
      throw new IllegalStateException("This participation is already cancelled.");
    }

    this.cancelled = cancelled;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || (obj instanceof IParticipationDto
                           && ((IParticipationDto) obj).getCompany() == this.getCompany()
                           && ((IParticipationDto) obj).getBusinessDay() == this.getBusinessDay());
  }

  @Override
  public int hashCode() {
    int result = businessDay;
    result = 31 * result + company;
    return result;
  }

  @Override
  public String toString() {
    return "ParticipationEntity{"
           + "state=" + state
           + ", businessDay=" + businessDay
           + ", company=" + company
           + ", cancelled=" + cancelled
           + '}';
  }
}
