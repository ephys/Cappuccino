package paoo.cappuccino.ihm.temp_do_not_delete.util;

import javax.swing.*;

/**
 * Basic frame setting properties shared by the app's different frames.
 */
public abstract class BaseFrame extends JFrame {
  public BaseFrame(String title, int width, int height) {
    super(title);

    setSize(width, height);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
