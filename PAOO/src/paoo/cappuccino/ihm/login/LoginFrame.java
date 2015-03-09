package paoo.cappuccino.ihm.login;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Frame for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class LoginFrame extends BaseFrame {

  /**
   * Creates a new frame for the login gui.
   *
   * @param userUcc The app User use case controller.
   * @param guiManager The manager reponsible for opening this frame.
   */
  @Inject
  public LoginFrame(IUserUcc userUcc, IGuiManager guiManager) {
    super("Connexion", 400, 240);
    this.setResizable(false);

    this.add(new LoginViewController(new LoginModel(userUcc), guiManager));
    this.setVisible(true);
  }
}
