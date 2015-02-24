package ihm;

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
  private JTextField text;
  private JLabel error;

  public JPanelTextError(String string, int i) {
    this.setLayout(new GridLayout(0, 1));
    text = new JTextField(string, i);
    error = new JLabel();
    error.setForeground(Color.RED);
    this.add(text);
    this.add(error);
  }

  public String getText() {
    return text.getText();
  }

  public void setText(String text) {
    this.text.setText(text);
  }

  public void setError(String error) {
    this.error.setText(error);
  }

}
