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
  private JFrame currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector) {
    this.injector = injector;
  }

  @Override
  public JFrame openFrame(Class<? extends JFrame> frameClass) {
    if (currentFrame != null) {
      (currentFrame).setVisible(false);
      (currentFrame).dispose();
    }

    currentFrame = injector.buildDependency(frameClass);

    return currentFrame;
  }
}
