package paoo.cappuccino.ihm.login;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.core.IGuiManager;

public class TestController extends JPanel implements IGuiManager.IManageableGui {
  private IGuiManager guiManager;
  private LoginModel model;

  public TestController(LoginModel model) {
    this.model = model;
  }

  @Override
  public void setManager(IGuiManager manager) {
    guiManager = manager;
  }

  @Override
  public void setupGui() {
    JButton button = new JButton("change frame");
    button.addActionListener(e -> guiManager.openFrame(/* TestOtherFrame.class */null));

    // take user input and send actions to model.
  }
}
