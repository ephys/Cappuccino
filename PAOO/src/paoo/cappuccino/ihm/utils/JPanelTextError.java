package paoo.cappuccino.ihm.utils;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * classe d'un label + erreure possible en dessous
 *
 * @author mopsome
 *
 */
public class JPanelTextError extends JPanel {
  private JTextField input;
  private JLabel error;
  private JLabel label;

  /**
   * constructor
   *
   * @param String the textarea's template
   * @param String description textarea
   */
  public JPanelTextError(String description, String exemple) {
    this.setLayout(new GridLayout(2, 0, Constantes.MGap, 0));
    this.input = new JTextField(13);
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
  public JPanelTextError(String label) {
    this(label, "");
  }

  /**
   * Get Input textarea
   * 
   * @return String input
   */
  public String getInput() {
    return input.getText();
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
