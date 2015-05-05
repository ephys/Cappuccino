package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IAttendanceDto;

public interface IAttendance extends IAttendanceDto, IVersionedEntity {

  /**
   * (un)cancels the participation of a contact to a business day.
   *
   * @param cancelled the contact cancelled his participation.
   */
  void setCancelled(boolean cancelled);
}
