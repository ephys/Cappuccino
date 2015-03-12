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
  public JFrame openFrame(Class<? extends JFrame> frame);

  /**
   * 
   *
   * @param user the user dto to pass by
   * @param frame the frame class
   * @return the new window
   */
  public JFrame openFrame(Class<? extends JFrame> frame, IUserDto user);
}
