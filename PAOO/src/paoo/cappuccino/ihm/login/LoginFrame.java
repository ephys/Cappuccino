package paoo.cappuccino.ihm.login;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

public class LoginFrame extends BaseFrame {

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public LoginFrame(IUserUcc userUcc, IGuiManager guiManager) {
    super("Connexion", 400, 240);
    this.setResizable(false);

    this.add(new LoginViewController(new LoginModel(userUcc), guiManager));
    this.setVisible(true);
  }
}
