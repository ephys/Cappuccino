package paoo.cappuccino.ihm.temp_do_not_delete;

import javax.swing.*;

import paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager;
import paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager.IManageableFrame;
import paoo.cappuccino.ihm.temp_do_not_delete.util.BaseFrame;

public class TestFrame extends BaseFrame implements IManageableFrame {

  private JPanel currentView;
  private IGuiManager guiManager;

  public TestFrame() {
    super("Test Frame", 500, 500);
  }

  @Override
  public void setManager(IGuiManager manager) {
    this.guiManager = manager;

    if (currentView instanceof IGuiManager.IManageableGui) {
      ((IGuiManager.IManageableGui) currentView).setManager(manager);
    }
  }

  @Override
  public void setupGui() {
    guiManager.setFrameViewController(TestViewController.class);
  }

  @Override
  public void setMainViewController(JPanel viewController) {
    currentView = viewController;

    this.add(viewController);
  }
}
