package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.util.StringUtils;

/**
 * Class implementing the IContact entity.
 *
 * @author Nicolas Fischer
 */
final class ContactEntity extends BaseEntity implements IContact {

  private int companyId;
  private String firstName;
  private String lastName;
  private String phone;
  private String email;
  private boolean emailValid;

  public ContactEntity(int companyId, String email, boolean emailValid, String firstName,
      String lastName, String phone) {
    this(-1, 0, companyId, email, emailValid, firstName, lastName, phone);
  }

  public ContactEntity(int id, int version, int companyId, String email, boolean emailValid,
      String firstName, String lastName, String phone) {
    super(id, version);
    this.companyId = companyId;
    this.emailValid = emailValid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
  }

  @Override
  public int getCompany() {
    return this.companyId;
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  @Override
  public void setEmail(String email) {
    if (email != null && !StringUtils.isEmail(email)) {
      throw new IllegalArgumentException("Invalid email " + email);
    }

    this.email = email;
    this.setEmailValid(true);
  }

  @Override
  public boolean isEmailValid() {
    return this.emailValid;
  }

  @Override
  public void setEmailValid(boolean emailValid) {
    this.emailValid = emailValid;
  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public String getPhone() {
    return this.phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this
        || (obj instanceof IContactDto && ((IContactDto) obj).getId() == this.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "ContactEntity{" + "companyId=" + companyId + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\'' + ", phone='" + phone + '\'' + ", email='" + email
        + '\'' + ", emailValid=" + emailValid + '}';
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public void setFirsName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public void setCompany(int company) {
    this.companyId = company;
  }
}
