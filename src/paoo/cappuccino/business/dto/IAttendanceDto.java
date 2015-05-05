package paoo.cappuccino.business.dto;

public interface IAttendanceDto extends IVersionnedDto {

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
   * Returns true if attendance has been cancelled.
   */
  boolean isCancelled();

}
