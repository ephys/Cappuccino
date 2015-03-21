package paoo.cappuccino.ihm.util;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

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
  public BaseFrame(String title, int width, int height,
      IGuiManager guiManager) {
    super(title);

    setIconImage(guiManager.getResourceManager().fetchImage(
        IhmConstants.PATH_LOGO));

    setSize(width, height);
    // setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    setupDefaultLookAndFeel();
  }

  /**
   * Setups the default style for the application.
   */
  protected void setupDefaultLookAndFeel() {
    setDefaultFont(new Font("Arial", Font.PLAIN, 16));
  }

  /**
   * Replaces the default font of the swing components.
   */
  protected void setDefaultFont(Font font) {
    UIDefaults defaultComponents = UIManager.getDefaults();
    Enumeration<Object> keys = defaultComponents.keys();

    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = defaultComponents.get(key);

      if (value == null || !(value instanceof FontUIResource)) {
        continue;
      }

      float fontSize = ((FontUIResource) value).getSize();
      UIManager.put(key, font.deriveFont(fontSize));
    }
  }
}
