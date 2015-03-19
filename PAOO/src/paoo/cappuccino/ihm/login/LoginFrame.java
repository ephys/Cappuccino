package paoo.cappuccino.ihm.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.core.injector.NoCache;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Frame for the Login Gui.
 *
 * @author Opsomer Mathias
 */
@NoCache
public class LoginFrame extends BaseFrame {

  private static final long serialVersionUID = 7523864408691215725L;

  /**
   * Creates a new frame for the login gui.
   *
   * @param userUcc    The app User use case controller.
   * @param guiManager The manager reponsible for opening this frame.
   */
  @Inject
  public LoginFrame(IUserUcc userUcc, IGuiManager guiManager, AppContext context) {
    super(context.getAppName(), 440, 490, guiManager);

    this.setMinimumSize(new Dimension(350, 380));

    this.setLayout(new GridBagLayout());
    this.add(new LoginViewController(new LoginModel(userUcc), guiManager),
             new GridBagConstraints());

    this.getContentPane().setBackground(new Color(212, 82, 82));

    this.setVisible(true);
  }
}
