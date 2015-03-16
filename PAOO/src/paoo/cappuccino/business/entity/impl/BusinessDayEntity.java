package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IBusinessDay;

/**
 * TODO, class javadoc.
 */
public class BusinessDayEntity extends BaseEntity implements IBusinessDay {

  private final LocalDateTime eventDate;
  private LocalDateTime creationDate; // TODO mark final

  /**
   * TODO.
   */
  public BusinessDayEntity(LocalDateTime eventDate) {
    this(-1, 0, eventDate);
    this.creationDate = LocalDateTime.now();
  }

  /**
   * TODO.
   */
  public BusinessDayEntity(int id, int version, LocalDateTime eventDate) {
    super(id, version);
    this.eventDate = eventDate;
    // TODO missing creation date
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
