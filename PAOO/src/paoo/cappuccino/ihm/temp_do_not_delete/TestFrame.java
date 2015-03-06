package paoo.cappuccino.ihm.temp_do_not_delete;

import javax.swing.JPanel;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager;
import paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager.IManageableGui;
import paoo.cappuccino.ihm.temp_do_not_delete.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

public class TestFrame extends BaseFrame implements IManageableGui {

  private JPanel currentView;
  private IGuiManager guiManager;

  private final IUserUcc uUcc;

  @Inject
  public TestFrame(IUserUcc uUcc) {
    super("Test Frame", 500, 500);

    this.uUcc = uUcc;
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
    currentView = new TestViewController(new TestModel(uUcc));
    ((IGuiManager.IManageableGui) currentView).setManager(guiManager);

    this.add(currentView);
  }
}
