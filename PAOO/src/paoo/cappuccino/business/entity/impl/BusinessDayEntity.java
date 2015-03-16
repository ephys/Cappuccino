package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IBusinessDay;

public class BusinessDayEntity extends BaseEntity implements IBusinessDay {

  private LocalDateTime eventDate;
  private LocalDateTime creationDate;

  public BusinessDayEntity(LocalDateTime eventDate) {
    this(-1, 0, eventDate);
    this.creationDate = LocalDateTime.now();
  }

  public BusinessDayEntity(int id, int version, LocalDateTime eventDate) {
    super(id, version);
    this.eventDate = eventDate;
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
