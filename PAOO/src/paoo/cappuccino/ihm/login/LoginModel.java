package paoo.cappuccino.ihm.login;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * Model for the Login View/ViewController.
 *
 * @author Opsomer Mathias
 */
public class LoginModel extends BaseModel {

  private final IUserUcc userUcc;
  private String passwordError;
  private String usernameError;

  @Inject
  public LoginModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }

  /**
   * Tries to log the user in.
   *
   * @param username The user's username.
   * @param password The user's password.
   * @return true: the user logged in.
   */
  public boolean attemptLogin(String username, char[] password) {
    resetErrors();

    boolean isValid = true;
    if (StringUtils.isEmpty(username)) {
      usernameError = IhmConstants.ERROR_FIELD_EMPTY;
      isValid = false;
    }

    if (password.length == 0) {
      passwordError = IhmConstants.ERROR_FIELD_EMPTY;

      isValid = false;
    }

    if (!isValid) {
      dispatchChangeEvent();
      return false;
    }

    IUserDto user = userUcc.logIn(username, password);

    if (user == null) {
      usernameError = IhmConstants.ERROR_WRONG_LOGIN;
      passwordError = IhmConstants.ERROR_WRONG_LOGIN;
    } else {
      // avoid password release in case of memory dump.
      StringUtils.clearString(password);
    }

    dispatchChangeEvent();
    return user != null;
  }

  /**
   * Clears the form errors.
   */
  private void resetErrors() {
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

}
