/**
 *
 */
package paoo.cappuccino.ihm.utils;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class JPanelPasswordError extends JPanel {
  private JPasswordField input;
  private JLabel error;
  private JLabel label;

  /**
   * constructor
   *
   * @param String the textarea's template
   * @param String description textarea
   */
  public JPanelPasswordError(String description, String exemple) {
    this.setLayout(new GridLayout(2, 0, Constantes.MGap, 0));
    this.input = new JPasswordField(13);
    this.label = new JLabel(description + " : ");
    this.label.setFont(Constantes.arial16);
    this.error = new JLabel(exemple);
    this.error.setForeground(Color.RED);

    this.add(label);
    this.add(input);
    this.add(new JLabel());
    this.add(error);
  }

  /**
   * constructor only with label
   *
   * @param label description textarea
   */
  public JPanelPasswordError(String label) {
    this(label, "");
  }

  /**
   * Get Input passwordField
   * 
   * @return char[] input
   */
  public char[] getInput() {
    return input.getPassword();
  }

  /**
   * setError
   * 
   * @param String new error
   */
  public void setError(String error) {
    this.error.setText(error);
  }

}
