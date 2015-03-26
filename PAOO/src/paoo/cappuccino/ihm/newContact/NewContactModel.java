package paoo.cappuccino.ihm.newContact;

import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the new Company View/ViewController.
 *
 * @author Opsomer mathias
 */
public class NewContactModel extends BaseModel {

  private String firstNameError;
  private String lastNameError;
  private String mailError;
  private String phoneError;


  public String getFirstNameError() {
    return firstNameError;
  }

  public String getLastNameError() {
    return lastNameError;
  }

  public String getMailError() {
    return mailError;
  }

  public String getPhoneError() {
    return phoneError;
  }

  public void setFirstNameError(String error) {
    firstNameError = error;
  }

  public void setNameError(String error) {
    this.firstNameError = error;
  }

  public void setLastNameError(String error) {
    this.lastNameError = error;
  }

  public void setMailError(String error) {
    this.mailError = error;
  }

  public void setPhoneError(String error) {
    this.phoneError = error;
  }


  public boolean hasError() {
    if (firstNameError != null || phoneError != null || mailError != null
        || lastNameError != null) {
      dispatchChangeEvent();
      return true;
    }
    return false;
  }

  public void clearError() {
    phoneError = null;
    mailError = null;
    firstNameError = null;
    lastNameError = null;
    dispatchChangeEvent();
  }
}
