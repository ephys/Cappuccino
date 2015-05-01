package paoo.cappuccino.business.dto;

import paoo.cappuccino.business.entity.IBaseEntity;

public interface IAttendanceDto extends IBaseEntity {

  /**
   * Returns the id of the business day of the attendance.
   */
  int getBusinessDay();

  /**
   * Returns the id of the company attending the business day.
   */
  int getCompany();

  /**
   * Gets id of the contact attending the business day.
   */
  int getContact();

  /**
   * Return if attendance is cancelled.
   */
  boolean isCancelled();

}
