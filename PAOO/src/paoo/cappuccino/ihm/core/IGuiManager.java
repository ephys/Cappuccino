package paoo.cappuccino.ihm.core;

import javax.swing.JFrame;

import paoo.cappuccino.business.dto.IUserDto;

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
  public <A extends JFrame> A openFrame(Class<A> frame);
}
