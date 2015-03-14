package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IContact;

public class Contact extends BaseEntity implements IContact {

  public Contact(int id) {
    super(id);
    // TODO Auto-generated constructor stub
  }

  @Override
  public int getCompany() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getEmail() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isEmailValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getFirstName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getLastName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPhone() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setEmail(String email) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setEmailValid(boolean emailValid) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setPhone(String phone) {
    // TODO Auto-generated method stub

  }
  /*
   * @Override public IParticipationDto[] getParticipations() { // TODO Auto-generated method stub
   * return null; }
   */
}
