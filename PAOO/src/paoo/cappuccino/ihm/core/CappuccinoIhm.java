package paoo.cappuccino.ihm.core;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

/**
 * Gui Manager, prepares frames and view controllers and handles what frame is currently open.
 *
 * @author Guylian Cox
 */
class CappuccinoIhm implements IGuiManager {

  private final IResourceManager resourceManager = new CachedResourceManager(this);
  private final Logger logger;
  private final DependencyInjector injector;

  private JFrame currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector, AppContext app) {
    this.injector = injector;
    this.logger = app.getLogger("ihm");

    app.addCrashListener(fatalException ->
                             JOptionPane.showMessageDialog(
                                 currentFrame,
                                 fatalException.getMessage(),
                                 "Une erreur est survenue",
                                 JOptionPane.ERROR_MESSAGE));
  }

  @Override
  public <A extends JFrame> A openFrame(Class<A> frameClass) {
    closeFrame();

    A frame = injector.buildDependency(frameClass);
    currentFrame = frame;

    return frame;
  }

  @Override
  public Logger getLogger() {
    return logger;
  }

  @Override
  public IResourceManager getResourceManager() {
    return resourceManager;
  }

  private void closeFrame() {
    if (currentFrame != null) {
      currentFrame.setVisible(false);
      currentFrame.dispose();
    }
  }
}
