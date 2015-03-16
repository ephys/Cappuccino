package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.util.StringUtils;

public class Contact extends BaseEntity implements IContact {
  private int CompanyId;
  private String email;
  private boolean emailValid;
  private String firstName;
  private String lastName;
  private String phone;

  public Contact(int companyId, String email, boolean emailValid, String firstName,
      String lastName, String phone) {
    this(-1, 0, companyId, email, emailValid, firstName, lastName, phone);
  }

  public Contact(int id, int version, int companyId, String email, boolean emailValid,
      String firstName, String lastName, String phone) {
    super(id, version);
    CompanyId = companyId;
    this.email = email;
    this.emailValid = emailValid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
  }

  @Override
  public int getCompany() {
    return this.CompanyId;
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  @Override
  public boolean isEmailValid() {
    return this.emailValid;
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
  public void setEmail(String email) {
    if (StringUtils.isEmail(email))
      this.email = email;
  }

  @Override
  public void setEmailValid(boolean emailValid) {
    this.emailValid = emailValid;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;

  }
}
