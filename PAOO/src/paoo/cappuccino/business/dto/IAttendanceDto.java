package paoo.cappuccino.business.dto;

public interface IAttendanceDto {

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
}
