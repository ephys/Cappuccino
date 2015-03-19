package paoo.cappuccino.ihm.util;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Input panel contaning a label, input field and error.
 *
 * @author mopsome
 */
public class ErrorableTextField extends JPanel {


  private static final long serialVersionUID = -4802276683409889790L;
  private JLabelFont error;

  /**
   * Creates an input panel.
   *
   * @param field        An input field.
   * @param label        The field's description.
   * @param errorMessage The error to display.
   */
  public ErrorableTextField(JTextField field, String label, String errorMessage) {
    super(new GridLayout(2, 2, IhmConstants.M_GAP, 0));

    this.error = new JLabelFont(errorMessage, 12);
    this.error.setForeground(Color.RED);

    field.setBorder(BorderFactory.createCompoundBorder(
        field.getBorder(),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    this.add(new JLabelFont(label + " : "));
    this.add(field);
    this.add(new JLabel());
    this.add(this.error);
  }

  /**
   * Creates an input panel without a default error.
   *
   * @param field An input field.
   * @param label The field's description.
   */
  public ErrorableTextField(JTextField field, String label) {
    this(field, label, null);
  }

  public void setError(String error) {
    this.error.setText(error);
  }
}
