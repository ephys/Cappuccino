package paoo.cappuccino.ihm;

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
   * @param int la taille du Jpaneltext
   */
  public JPanelTextError(String string, int taille) {
    this.setLayout(new GridLayout(0, 1));
    text = new JTextField(string, taille);
    this.add(text);
   
  }

  /**
   * constructeur avec uniquement la taille
   *
   * @param taille la taille du JPanelText
   */
  public JPanelTextError(int taille) {
    this("", taille);
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
  
  
  public void setErrorLabel(JLabel error){
	  
	  this.error = error;
	  this.error.setForeground(Color.RED);
  }

}
