package paoo.cappuccino.ihm.temp_do_not_delete.core;

import javax.swing.*;

import paoo.cappuccino.core.injector.Singleton;

@Singleton
/**
 * Used to manage the application GUIs
 *
 * @author Guylian Cox
 */
public interface IGuiManager {

  /**
   * Closes the current window (if exists) and creates a new one
   *
   * @param frame the frame class, must implement
   *              {@link paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager.IManageableFrame}
   * @return the new window
   */
  public JFrame openFrame(Class<? extends JFrame> frame);

  /**
   * Changes the current frame's main view controller.
   * This is used to change the main section without resetting the header and nav
   *
   * @param vcClass The view controller class, if it implements
   *                {@link paoo.cappuccino.ihm.temp_do_not_delete.core.IGuiManager.IManageableGui},
   *                it will receive the gui manager instance.
   * @return the new view controller
   */
  public JPanel setFrameViewController(Class<? extends JPanel> vcClass);

  /**
   * Marks a Gui as being manageable by the GuiManager.
   *
   * @author Guylian Cox
   */
  public static interface IManageableGui {

    /**
     * Sets the reference to the current Gui Manager.
     * @param manager the current Gui Manager
     */
    public void setManager(IGuiManager manager);

    /**
     * Called when the Gui Manager is done building and setting up the Gui.
     */
    public void setupGui();
  }

  /**
   * Marks a window/frame as being manageable by the GuiManager.
   *
   * @author Guylian Cox
   */
  public static interface IManageableFrame extends IManageableGui {

    /**
     * Changes the frame's main view controller
     * @param viewController a view controller
     */
    public void setMainViewController(JPanel viewController);
  }
}
