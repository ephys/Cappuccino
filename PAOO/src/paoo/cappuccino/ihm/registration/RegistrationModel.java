package paoo.cappuccino.ihm.registration;

import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the registration View/ViewController.
 *
 * @author Maduka Junior
 */
public class RegistrationModel extends BaseModel {

  private String passwordError;
  private String usernameError;
  private String confirmPasswordError;
  private String lastNameError;
  private String firstNameError;
  private String emailError;

  /**
   * Sets the error of the registration form.
   * @param passwordError Error message relative to the password field.
   * @param usernameError Error message relative to the username field.
   * @param confirmPasswordError Error message relative to the password confirmation field.
   * @param lastNameError Error message relative to the last name field.
   * @param firstNameError Error message relative to the first name field.
   * @param emailError Error message relative to the email field.
   */
  public void setErrors(String passwordError, String usernameError, String confirmPasswordError,
                         String lastNameError, String firstNameError, String emailError) {
    this.passwordError = passwordError;
    this.usernameError = usernameError;
    this.confirmPasswordError = confirmPasswordError;
    this.lastNameError = lastNameError;
    this.firstNameError = firstNameError;
    this.emailError = emailError;

    dispatchChangeEvent();
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

  /**
   * Gets the errors relative to the confirm password input.
   */
  public String getConfirmPasswordError() {
    return confirmPasswordError;
  }

  /**
   * Gets the errors relative to the last name input.
   */
  public String getLastNameError() {
    return lastNameError;
  }

  /**
   * Gets the errors relative to the first name input.
   */
  public String getFirstNameError() {
    return firstNameError;
  }

  /**
   * Gets the errors relative to the email input.
   */
  public String getEmailError() {
    return emailError;
  }

}
