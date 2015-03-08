package paoo.cappuccino.ihm.login;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.utils.IhmConstants;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;

public class LoginModel extends BaseModel {

  private final IUserUcc userUcc;
  private String passwordError;
  private String usernameError;
  private String formError;

  @Inject
  public LoginModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }

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
      formError = "Mot de passe ou login incorrect.";
    } else {
      // avoid password release in case of memory dump.
      StringUtils.clearString(password);
    }

    dispatchChangeEvent();
    return user != null;
  }

  private void resetErrors() {
    passwordError = null;
    usernameError = null;
    formError = null;
  }

  public String getUsernameError() {
    return usernameError;
  }

  public String getPasswordError() {
    return passwordError;
  }

  public String getFormError() {
    return formError;
  }
}
