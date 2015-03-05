package paoo.cappuccino.ihm.utils;

import java.awt.*;

import javax.swing.*;

/**
 * Description + input + (error)
 *
 * @author mopsome
 *
 */
public class JPanelTextError extends JPanel {

  private static final long serialVersionUID = -714442590193004703L;
  private JTextField input;
  private JLabelFont error;
  private JLabelFont label;

  /**
   * Constructor
   *
   * @param String the textarea's template
   * @param String description textarea
   */
  public JPanelTextError(String description, String exemple) {
    this.setLayout(new GridLayout(2, 0, Constantes.MGap, 0));
    input = new JTextField();
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
   * SetError
   *
   * @param String new error
   */
  public void setError(String error) {
    this.error.setText(error);
  }

}
