package paoo.cappuccino.ihm.utils;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Specialiation of a JLabel with a different Font
 *
 * @author Opsomer Mathias
 *
 */
public class JLabelFont extends JLabel {

  private static final long serialVersionUID = 5620229542204605508L;

  /**
   * Constructor JLabelFont
   *
   * @param texte the texte to display
   * @param size the font's size
   */
  public JLabelFont(String texte, int size) {
    super(texte);
    Font arial = new Font("Arial", Font.PLAIN, size);
    this.setFont(arial);
  }

  /**
   * Constructor JLabelFont with Default size = 16
   * 
   * @param texte the texte to display
   */
  public JLabelFont(String texte) {
    this(texte, 16);
  }

  /**
   * Constructor JLabelFont with default size = 16 and texte = "";
   */
  public JLabelFont() {
    this("", 16);
  }
}
