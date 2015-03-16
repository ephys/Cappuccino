package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.util.StringUtils;

/**
 * TODO.
 */
public class Contact extends BaseEntity implements IContact {

  private final int companyId;
  private final String firstName;
  private final String lastName;
  private String phone;
  private String email;
  private boolean emailValid;

  /**
   * TODO.
   */
  public Contact(int companyId, String email, boolean emailValid, String firstName,
                 String lastName, String phone) {
    this(-1, 0, companyId, email, emailValid, firstName, lastName, phone);
  }

  /**
   * TODO.
   */
  public Contact(int id, int version, int companyId, String email, boolean emailValid,
                 String firstName, String lastName, String phone) {
    super(id, version);
    this.companyId = companyId;
    this.emailValid = emailValid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;

    setEmail(email);
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
    if (!StringUtils.isEmail(email)) {
      throw new IllegalArgumentException("Invalid email " + email);
    }

    this.email = email;
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
}
