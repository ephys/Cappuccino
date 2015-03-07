package paoo.cappuccino.ihm.login;

import javax.swing.*;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.core.IGuiManager.IManageableGui;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

public class LoginFrame extends BaseFrame implements IManageableGui {

  private final IUserUcc userUcc;
  private JPanel currentView;
  private IGuiManager guiManager;

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public LoginFrame(IUserUcc userUcc) {
    super("Connexion", 500, 500);

    this.userUcc = userUcc;
  }

  @Override
  public void setManager(IGuiManager manager) {
    this.guiManager = manager;

    if (currentView != null) {
      ((IGuiManager.IManageableGui) currentView).setManager(manager);
    }
  }

  @Override
  public void setupGui() {
    this.setResizable(false);

    currentView = new TestViewController(new LoginModel(userUcc));
    ((IGuiManager.IManageableGui) currentView).setManager(guiManager);

    this.add(currentView);

    this.setVisible(true);
  }
}
