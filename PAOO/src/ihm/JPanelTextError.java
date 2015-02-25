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

  /**
   * constructeur pour un JPanelTextError
   * 
   * @param string le string d'exemple
   */
  public JPanelTextError(String string) {
    this.setLayout(new GridLayout(0, 1));
    text = new JTextField(string);
    error = new JLabel();
    error.setForeground(Color.RED);
    this.add(text);
    this.add(error);
  }

  /**
   * get texte
   * 
   * @return String le text contenu dans le label
   */
  public String getText() {
    return text.getText();
  }

  /**
   * set le text du label
   *
   * @param text le texte de remplacement
   */
  public void setText(String text) {
    this.text.setText(text);
  }

  /**
   * set le text de l'erreur liée au label
   *
   * @param error l'erreure
   */
  public void setError(String error) {
    this.error.setText(error);
  }

}
