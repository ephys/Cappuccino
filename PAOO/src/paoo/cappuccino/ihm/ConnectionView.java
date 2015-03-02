package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * classe vue de la connection
 *
 * @author Opsomer Mathias
 *
 */
public class ConnectionView extends JFrame {
  private Font arial = new Font("Arial", Font.PLAIN, 16);
  private JPanelTextError username;
  private JPanelPasswordError password;



  /**
   * constructeur
   */
  public ConnectionView() {
    this.setTitle("connexion");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 240);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    JLabel errorUserMessage = new JLabel();
    JLabel errorPasswordMessage = new JLabel();
    JPanel fields = new JPanel(new GridLayout(0,1));
    JPanel withinUserField = new JPanel(new GridLayout());
    JPanel withinPasswordField = new JPanel(new GridLayout());
    JPanel userField = new JPanel();
    JPanel passwordField = new JPanel();
    
    JLabel user = new JLabel("Nom d'utilisateur : ");
    user.setFont(arial);
   

    JLabel pwd = new JLabel("mot de passe : ");
    pwd.setFont(arial);

     username = new JPanelTextError(13);
     userField.add(user);
     userField.add(username);
     withinUserField.add(new JLabel());
     withinUserField.add(errorUserMessage);
     userField.add(withinUserField);
     username.setErrorLabel(errorUserMessage);
     
     password = new JPanelPasswordError(13);
     passwordField.add(pwd);
     passwordField.add(password);
     withinPasswordField.add(new JLabel());
     withinPasswordField.add(errorPasswordMessage);
     passwordField.add(withinPasswordField);
     password.setErrorLabel(errorPasswordMessage);
     
     fields.add(new JPanel());
     fields.add(userField);
     fields.add(passwordField);

     
    this.add(fields);
    JPanel centreBoutons = new JPanel();
    centreBoutons.add(new ConnectionController(this));
    centreBoutons.setAlignmentX(CENTER_ALIGNMENT);
    this.add(centreBoutons, BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /**
   * @author Opsomer mathias
   *
   * @param string
   */
  public void setTextError(String string) {
    this.username.setError(string);
  }

  /**
   *
   * @author Opsomer Mathias
   *
   */
  public void removeTextError() {
    this.username.setError("");
  }

  /**
   * @author Opsomer Mathias
   *
   * @param string
   */
  public void setPasswordError(String string) {
    this.password.setError(string);
  }

  /**
   *
   * @author Opsomer Mathias
   *
   */
  public void removePasswordError() {
    this.password.setError("");
  }

  /**
   * get username
   *
   * @return String le contenu du JTextField
   */
  public String getUsername() {
    return username.getText();
  }

  /**
   * get password
   *
   * @return char[] le password
   */
  public char[] getPassword() {
    return password.getPassword();
  }

  /**
   * classe interne pour le password field + erreure
   *
   * @author Opsomer Mathias
   *
   */

}
