package paoo.cappuccino.ihm.core;

import javax.swing.JFrame;

import paoo.cappuccino.core.injector.Singleton;

@Singleton
/**
 * Used to manage the application GUIs.
 *
 * @author Guylian Cox
 */
public interface IGuiManager {

  /**
   * Closes the current window (if exists) and creates a new one
   *
   * @param frame the frame class, must implement
   *        {@link paoo.cappuccino.ihm.core.IGuiManager.IManageableGui}
   * @return the new window
   */
  public JFrame openFrame(Class<? extends JFrame> frame);

  /**
   * Marks a Gui as being manageable by the GuiManager.
   *
   * @author Guylian Cox
   */
  public static interface IManageableGui {

    /**
     * Sets the reference to the current Gui Manager.
     *
     * @param manager the current Gui Manager
     */
    public void setManager(IGuiManager manager);

    /**
     * Called when the Gui Manager is done building and setting up the Gui.
     */
    public void setupGui();
  }
}
