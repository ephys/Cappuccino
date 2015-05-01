package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IAttendanceDto;

public interface IAttendance extends IAttendanceDto {

  /**
   * set the boolean cancelled of the attendance
   * 
   * @param b the boolean
   */
  void setCancelled(Boolean b);

}
