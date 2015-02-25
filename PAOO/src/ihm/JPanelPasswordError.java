/**
 * 
 */
package ihm;

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
  private JPasswordField password;
  private JLabel error;

  /**
   * constructeur d'un JpanelPassword Error
   * 
   * @param string le string d'initialisation
   * @param taille la taille du Passwordfield
   */
  public JPanelPasswordError(String string, int taille) {
    this.setLayout(new GridLayout(0, 1));
    password = new JPasswordField(string, taille);
    error = new JLabel();
    error.setForeground(Color.RED);
    this.add(password);
    this.add(error);

  }

  /**
   * constructeur avec rien que la taille
   * 
   * @param taille la taille du Passwordfield
   */
  public JPanelPasswordError(int taille) {
    this("", taille);
  }

  /**
   * get le mot de passe du JPasswordField
   *
   * @return char[] le password
   */
  public char[] getPassword() {
    return password.getPassword();
  }

  /**
   * get l'erreure liée au JPasswordField
   *
   * @return String l'erreure
   */
  public String getError() {
    return error.getText();
  }

  /**
   * set l'erreure liée au JpasswordField
   *
   * @param error l'erreure remplacente
   */
  public void setError(String error) {
    this.error.setText(error);
  }
}
