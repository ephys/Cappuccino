package paoo.cappuccino.ihm.core;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.exception.FatalException;

/**
 * Gui Manager, prepares frames and view controllers and handles what frame is currently open.
 *
 * @author Guylian Cox
 */
class CappuccinoIhm implements IGuiManager {

  private final DependencyInjector injector;
  private JFrame currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector, AppContext app) {
    this.injector = injector;

    app.addCrashListener(fatalException -> {
      JOptionPane.showMessageDialog(currentFrame,
          fatalException.getMessage(), "Une erreur est survenue",
          JOptionPane.ERROR_MESSAGE);

      if (fatalException instanceof FatalException) {
        closeFrame();
      }
    });
  }

  @Override
  public JFrame openFrame(Class<? extends JFrame> frameClass) {
    if (currentFrame != null)
      closeFrame();
    currentFrame = injector.buildDependency(frameClass);
    return currentFrame;
  }

  private void closeFrame() {
    if (currentFrame != null) {
      currentFrame.setVisible(false);
      currentFrame.dispose();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.ihm.core.IGuiManager#openFrame(java.lang.Class,
   * paoo.cappuccino.business.dto.IUserDto)
   */
  @Override
  public JFrame openFrame(Class<? extends JFrame> frame, IUserDto user) {
    // TODO Auto-generated method stub
    return null;
  }
}
