package paoo.cappuccino.ihm.utils;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * description of a JPasswordField and this linked error
 * 
 * @author Opsomer Mathias
 *
 */
public class JPanelPasswordError extends JPanel {

  private static final long serialVersionUID = -6170242877236427872L;
  private JPasswordField input;
  private JLabelFont error;
  private JLabelFont label;

  /**
   * Constructor
   *
   * @param String the textarea's template
   * @param String description textarea
   */
  public JPanelPasswordError(String description, String exemple) {
    this.setLayout(new GridLayout(2, 0, Constantes.MGap, 0));
    input = new JPasswordField();
    label = new JLabelFont(description + " : ");
    error = new JLabelFont(exemple, 12);
    error.setForeground(Color.RED);

    this.add(label);
    this.add(input);
    this.add(new JLabel());
    this.add(error);
  }

  /**
   * Constructor only with label
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
   * SetError
   *
   * @param String new error
   */
  public void setError(String error) {
    this.error.setText(error);
  }

}
