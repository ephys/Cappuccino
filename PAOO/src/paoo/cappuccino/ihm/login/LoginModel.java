package paoo.cappuccino.ihm.login;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ucc.IUserUcc;

public class LoginModel extends BaseModel {

  private final IUserUcc userUcc;

  @Inject
  public LoginModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }
}
