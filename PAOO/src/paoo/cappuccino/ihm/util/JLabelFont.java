package paoo.cappuccino.ihm.util;

import javax.swing.JLabel;

/**
 * Specialisation of a JLabel with a different font size.
 *
 * @author Opsomer Mathias
 */
public class JLabelFont extends JLabel {

  private static final long serialVersionUID = 5620229542204605508L;

  /**
   * Creates a new JLabel using a custom font size. The used font is defined by the default font
   * (See {@link paoo.cappuccino.ihm.util.BaseFrame#setDefaultFont(java.awt.Font)} to change it)
   *
   * @param text the text to display.
   * @param size the font size.
   */
  public JLabelFont(String text, float size) {
    super(text);

    this.setFont(this.getFont().deriveFont(size));
  }

  /**
   * Creates a new JLabel using a custom font size. The used font is defined by the default font
   * (See {@link paoo.cappuccino.ihm.util.BaseFrame#setDefaultFont(java.awt.Font)} to change it)
   *
   * @param text the text to display.
   */
  public JLabelFont(String text) {
    this(text, 12);
  }
}
