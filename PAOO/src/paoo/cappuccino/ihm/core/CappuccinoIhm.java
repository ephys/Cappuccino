package paoo.cappuccino.ihm.core;

import javax.swing.JFrame;

import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

/**
 * Gui Manager, prepares frames and view controllers and handles what frame is currently open.
 *
 * @author Guylian Cox
 */
class CappuccinoIhm implements IGuiManager {

  private final DependencyInjector injector;
  private IManageableGui currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector) {
    this.injector = injector;
  }

  @Override
  public JFrame openFrame(Class<? extends JFrame> frameClass) {
    if (!IManageableGui.class.isAssignableFrom(frameClass)) {
      throw new IllegalArgumentException("The frame class should implement IManageableFrame.");
    }

    if (currentFrame != null) {
      ((JFrame) currentFrame).setVisible(false);
      ((JFrame) currentFrame).dispose();
    }

    currentFrame = (IManageableGui) injector.buildDependency(frameClass);
    currentFrame.setManager(this);
    currentFrame.setupGui();

    return (JFrame) currentFrame;
  }
}
