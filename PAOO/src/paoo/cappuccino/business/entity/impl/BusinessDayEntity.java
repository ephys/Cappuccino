package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IBusinessDay;

/**
 * Class implementing the IBusinessDay entity.
 *
 * @author Nicolas Fischer
 */
final class BusinessDayEntity extends BaseEntity implements IBusinessDay {

  private final LocalDateTime eventDate;
  private final LocalDateTime creationDate;

  public BusinessDayEntity(LocalDateTime eventDate) {
    this(-1, 0, eventDate, null);
  }

  public BusinessDayEntity(int id, int version, LocalDateTime eventDate, LocalDateTime creationDate) {
    super(id, version);
    this.eventDate = eventDate;
    this.creationDate = creationDate;
  }

  @Override
  public LocalDateTime getEventDate() {
    return this.eventDate;
  }

  @Override
  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

}
