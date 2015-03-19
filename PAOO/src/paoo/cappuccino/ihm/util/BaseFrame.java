package paoo.cappuccino.ihm.util;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import paoo.cappuccino.ihm.core.IGuiManager;

/**
 * Basic frame, setting up properties shared by the app's different frames.
 *
 * @author Guylian Cox
 */
public abstract class BaseFrame extends JFrame {

  private static final long serialVersionUID = -5882096175427359233L;

  /**
   * Creates a new frame.
   *
   * @param title The frame title.
   * @param width The frame width.
   * @param height The frame height.
   */
  public BaseFrame(String title, int width, int height, IGuiManager guiManager) {
    super(title);

    setIconImage(guiManager.getResourceManager().fetchImage(IhmConstants.PATH_LOGO));

    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
