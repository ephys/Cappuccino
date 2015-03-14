package paoo.cappuccino.ihm.registration;

import java.util.Arrays;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * Model for the registration View/ViewController.
 *
 * @author Maduka Junior
 */
public class RegistrationModel extends BaseModel {

  private final IUserUcc userUcc;
  private String passwordError;
  private String usernameError;
  private String confirmPasswordError;
  private String lastNameError;
  private String firstNameError;
  private String emailError;

  @Inject
  public RegistrationModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }

  /**
   * Tries to register the user.
   *
   * @param username The user's username.
   * @param password The user's password.
   * @return a user or null if registration failed
   */
  public IUserDto attemptRegistration(String username, char[] password,
      char[] confirmPassword, String lastName, String firstName,
      String email) {
    String isValid = "";

    usernameError =
        StringUtils.isEmpty(username) ? IhmConstants.ERROR_FIELD_EMPTY
            : "";
    isValid += usernameError;
    lastNameError =
        StringUtils.isEmpty(lastName) ? IhmConstants.ERROR_FIELD_EMPTY
            : (!StringUtils.isAlphaString(lastName) ? IhmConstants.ERROR_INVALID_INPUT_STRING
                : "");
    isValid += lastNameError;
    firstNameError =
        StringUtils.isEmpty(firstName) ? IhmConstants.ERROR_FIELD_EMPTY
            : (!StringUtils.isAlphaString(firstName) ? IhmConstants.ERROR_INVALID_INPUT_STRING
                : "");
    isValid += firstNameError;
    emailError =
        StringUtils.isEmpty(email) ? IhmConstants.ERROR_FIELD_EMPTY
            : (!StringUtils.isEmail(email) ? IhmConstants.ERROR_INVALID_EMAIL
                : "");
    isValid += emailError;
    passwordError =
        password.length == 0 ? IhmConstants.ERROR_FIELD_EMPTY : "";
    isValid += passwordError;
    confirmPasswordError =
        confirmPassword.length == 0 ? IhmConstants.ERROR_FIELD_EMPTY
            : (!Arrays.equals(password, confirmPassword) ? IhmConstants.ERROR_NOT_MATCHING_PASSWORD
                : "");
    isValid += confirmPasswordError;

    dispatchChangeEvent();

    if (!isValid.equals("")) {
      return null;
      // return userUcc.registration(....);
    }
    return null;
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
  public String getlastNameError() {
    return lastNameError;
  }

  /**
   * Gets the errors relative to the first name input.
   */
  public String getfirstNameError() {
    return firstNameError;
  }

  /**
   * Gets the errors relative to the email input.
   */
  public String getemailError() {
    return emailError;
  }
}
