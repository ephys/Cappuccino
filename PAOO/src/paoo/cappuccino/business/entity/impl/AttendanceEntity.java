package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.IAttendance;

public class AttendanceEntity implements IAttendance {

  private final int businessDay;
  private final int company;
  private final int contact;

  /**
   * Creates a new attendance entity.
   */
  public AttendanceEntity(int businessDay, int company, int contact) {
    this.businessDay = businessDay;
    this.company = company;
    this.contact = contact;
  }

  @Override
  public int getBusinessDay() {
    return businessDay;
  }

  @Override
  public int getCompany() {
    return company;
  }

  @Override
  public int getContact() {
    return contact;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || !(obj instanceof IAttendanceDto)) {
      return false;
    }

    AttendanceEntity that = (AttendanceEntity) obj;
    return businessDay == that.businessDay && company == that.company && contact == that.contact;
  }

  @Override
  public int hashCode() {
    int result = businessDay;
    result = 31 * result + company;
    result = 31 * result + contact;
    return result;
  }

  @Override
  public String toString() {
    return "AttendanceEntity{"
           + "businessDay=" + businessDay
           + ", company=" + company
           + ", contact=" + contact
           + '}';
  }
}
