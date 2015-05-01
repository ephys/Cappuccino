package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.IAttendance;

public class AttendanceEntity implements IAttendance {

  private final int businessDay;
  private final int company;
  private final int contact;
  private int version;
  private boolean cancelled;

  /**
   * Creates a new attendance entity.
   * 
   */
  public AttendanceEntity(int businessDay, int company, int contact) {
    this(businessDay, company, contact, false, 0);
  }

  /**
   * Creates a new attendance entity
   * 
   */
  public AttendanceEntity(int businessDay, int company, int contact, boolean cancelled, int version) {
    this.businessDay = businessDay;
    this.company = company;
    this.contact = contact;
    this.version = version;
    this.cancelled = cancelled;
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
    return "AttendanceEntity{" + "businessDay=" + businessDay + ", company=" + company
        + ", contact=" + contact + '}';
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.business.dto.IAttendanceDto#isCancelled()
   */
  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.business.entity.IBaseEntity#incrementVersion()
   */
  @Override
  public int incrementVersion() {
    return ++this.version;
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.business.dto.IBaseDto#getId()
   */
  @Override
  public int getId() {
    return -1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.business.dto.IBaseDto#getVersion()
   */
  @Override
  public int getVersion() {
    return this.version;
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.business.entity.IAttendance#setCancelled(java.lang.Boolean)
   */
  @Override
  public void setCancelled(Boolean bool) {
    this.cancelled = bool;

  }
}
