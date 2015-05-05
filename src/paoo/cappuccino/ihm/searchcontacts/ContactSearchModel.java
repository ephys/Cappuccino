package paoo.cappuccino.ihm.searchcontacts;

import paoo.cappuccino.ihm.util.BaseModel;

public class ContactSearchModel extends BaseModel {

  private String firstName;
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
    dispatchChangeEvent();
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
    dispatchChangeEvent();
  }
}
