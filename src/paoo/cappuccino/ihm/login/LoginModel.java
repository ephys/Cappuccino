package paoo.cappuccino.ihm.login;

import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the Login View/ViewController.
 *
 * @author Opsomer Mathias
 */
public class LoginModel extends BaseModel {

  private String passwordError;
  private String usernameError;

  /**
   * Clears the form errors.
   */
  public void resetErrors() {
    passwordError = null;
    usernameError = null;
  }

  /**
   * Gets the errors relative to the username input.
   */
  public String getUsernameError() {
    return usernameError;
  }

  /**
   * Gets the errors relative to the password input.
   */
  public String getPasswordError() {
    return passwordError;
  }

  public void setUsernameError(String error) {
    usernameError = error;
    dispatchChangeEvent();
  }

  public void setPasswordError(String error) {
    passwordError = error;
    dispatchChangeEvent();
  }
}
