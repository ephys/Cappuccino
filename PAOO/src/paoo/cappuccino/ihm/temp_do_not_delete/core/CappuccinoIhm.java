package paoo.cappuccino.ihm.temp_do_not_delete.core;

import javax.swing.*;

import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

/**
 * Gui Manager, prepares frames and view controllers and handles what frame is currently open
 *
 * This gui manager injects dependencies in the viewcontrollers
 *
 * @author Guylian Cox
 */
public class CappuccinoIhm implements IGuiManager {

  private final DependencyInjector injector;
  private IManageableFrame currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector) {
    this.injector = injector;
  }

  //IGuiManager ihm = new CappuccinoIhm(DependencyInjector.INSTANCE);
  //ihm.openFrame(TestFrame.class);
  @Override
  public JFrame openFrame(Class<? extends JFrame> frameClass) {
    if (!IManageableGui.class.isAssignableFrom(frameClass)) {
      throw new IllegalArgumentException("The frame class should implement IManageableFrame.");
    }

    if (currentFrame != null) {
      ((JFrame) currentFrame).setVisible(false);
      ((JFrame) currentFrame).dispose();
    }

    currentFrame = (IManageableFrame) injector.buildDependency(frameClass);
    currentFrame.setManager(this);
    currentFrame.setupGui();

    return (JFrame) currentFrame;
  }

  @Override
  public JPanel setFrameViewController(Class<? extends JPanel> vcClass) {
    if (currentFrame == null) {
      throw new IllegalStateException("No frame is currently open.");
    }

    JPanel viewController = (JPanel) injector.buildDependency(vcClass);

    if (viewController instanceof IManageableGui) {
      ((IManageableGui) viewController).setManager(this);
      currentFrame.setMainViewController(viewController);

      ((IManageableGui) viewController).setupGui();
    } else {
      currentFrame.setMainViewController(viewController);
    }

    return viewController;
  }
}
