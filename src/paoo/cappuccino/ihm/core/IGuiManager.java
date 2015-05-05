package paoo.cappuccino.ihm.core;

import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * Used to manage the application GUIs.
 *
 * @author Guylian Cox
 */
public interface IGuiManager {

  /**
   * Closes the current window (if exists) and creates a new one.
   *
   * @param frame the frame class
   * @return the new window
   */
  <A extends JFrame> A openFrame(Class<A> frame);

  /**
   * Returns the currently open frame or null if none is open.
   */
  JFrame getCurrentFrame();

  
  /**
   * Returns the Gui logger.
   */
  Logger getLogger();

  /**
   * Returns the resource manager.
   */
  IResourceManager getResourceManager();

  /**
   * Invokes a Runnable using another thread than the swing one.
   */
  void invoke(Runnable runnable);
}
