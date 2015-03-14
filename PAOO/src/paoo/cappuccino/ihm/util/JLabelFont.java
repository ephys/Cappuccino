package paoo.cappuccino.ihm.util;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Specialisation of a JLabel with a different Font.
 *
 * @author Opsomer Mathias
 */
public class JLabelFont extends JLabel {

  private static final long serialVersionUID = 5620229542204605508L;

  /**
   * Creates a new JLabel using the Arial font.
   *
   * @param text the text to display.
   * @param size  the font size.
   */
  public JLabelFont(String text, int size) {
    super(text);

    Font arial = new Font("Arial", Font.PLAIN, size);
    this.setFont(arial);
  }

  /**
   * Creates a new JLabel using the Arial font and a font size of 16.
   *
   * @param text the text to display
   */
  public JLabelFont(String text) {
    this(text, 16);
  }

  /**
   * Creates a new JLabel using the Arial font and text.
   */
  public JLabelFont() {
    this(null);
  }
}
