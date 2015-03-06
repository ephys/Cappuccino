package paoo.cappuccino.ihm.temp_do_not_delete;

import javax.swing.JButton;
import javax.swing.JPanel;

import paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager;

public class TestController extends JPanel implements IGuiManager.IManageableGui {
  private IGuiManager guiManager;
  private TestModel model;

  public TestController(TestModel model) {
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
