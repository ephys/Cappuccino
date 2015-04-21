package paoo.cappuccino.ihm.newcontact;

import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the new contact screen.
 *
 * @author Opsomer mathias
 */
public class NewContactModel extends BaseModel {

  private String firstNameError;
  private String lastNameError;
  private String mailError;

  public String getFirstNameError() {
    return firstNameError;
  }

  public String getLastNameError() {
    return lastNameError;
  }

  public String getMailError() {
    return mailError;
  }

  /**
   * Sets the errors relative to the fields of the contact creation form.
   */
  public void setErrors(String firstNameError, String lastNameError, String mailError) {
    this.firstNameError = firstNameError;
    this.lastNameError = lastNameError;
    this.mailError = mailError;

    dispatchChangeEvent();
  }

  public boolean hasError() {
    return firstNameError != null || mailError != null || lastNameError != null;
  }

}
