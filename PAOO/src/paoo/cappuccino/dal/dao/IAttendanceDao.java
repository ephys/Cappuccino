package paoo.cappuccino.dal.dao;

import java.util.List;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.IAttendance;

public interface IAttendanceDao {
  /**
   * Inserts a new attendance in the database.
   *
   * @param attendance The attendance to insert.
   * @return The attendance entity with its information updated from the database.
   * @throws java.lang.IllegalArgumentException One of the fields failed to insert due to constraint
   *         violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IAttendanceDto createAttendance(IAttendanceDto attendance);

  /**
   * Fetches the list of attendances for a company and a given business day.
   *
   * @param companyId The id of the company attending a business day.
   * @param businessDayId The id of the business day to attend.
   * @return The list of attendances.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IAttendanceDto> fetchAttendances(int companyId, int businessDayId);

  /**
   * Fetches the list of attendances for a contact.
   *
   * @param contactId The id of the contact attending the business days.
   * @return The list of attendances.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IAttendanceDto> fetchAttendancesByContact(int contactId);

  /**
   * Update an attendance in the database.
   *
   * @param createAttendance the new attendance
   */
  void updateAttendance(IAttendance attendance);

}
