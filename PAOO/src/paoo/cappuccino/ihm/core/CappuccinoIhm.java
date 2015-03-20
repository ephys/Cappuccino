package paoo.cappuccino.ihm.core;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

  private final IResourceManager resourceManager = new CachedResourceManager(this);
  private final Logger logger;
  private final DependencyInjector injector;

  private JFrame currentFrame;

  @Inject
  public CappuccinoIhm(DependencyInjector injector, AppContext app) {
    this.injector = injector;
    this.logger = app.getLogger("ihm");

    // Catches every exception that goes through the IHM
    setupExceptionHandler();

    // Exception handler for fatal exceptions (the application will close after these).
    app.addCrashListener(this::displayException);
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

  /**
   * Destroys the current frame.
   */
  private void closeFrame() {
    if (currentFrame != null) {
      currentFrame.setVisible(false);
      currentFrame.dispose();
    }
  }

  /**
   * Setups the Gui's exception handler.
   */
  private void setupExceptionHandler() {
    Toolkit.getDefaultToolkit().getSystemEventQueue().push(new EventQueue() {
      protected void dispatchEvent(AWTEvent newEvent) {
        try {
          super.dispatchEvent(newEvent);
        } catch (Throwable t) {
          // Only rethrow exception that are meant to crash the application.
          if (t instanceof FatalException) {
            throw t;
          }

          logger.log(Level.SEVERE, "An exception has been caught by the Gui Manager", t);
          displayException(t);
        }
      }
    });
  }

  private void displayException(Throwable throwable) {
    JOptionPane.showMessageDialog(
        currentFrame,
        throwable.getMessage(),
        "Une erreur est survenue",
        JOptionPane.ERROR_MESSAGE);
  }
}
