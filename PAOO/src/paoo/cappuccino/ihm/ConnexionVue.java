package paoo.cappuccino.ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * classe vue de la connection
 *
 * @author Opsomer Mathias
 *
 */
public class ConnexionVue extends JFrame {
  private Font arial = new Font("Arial", Font.PLAIN, 16);
  private JPanelTextError username;
  private JPanelPasswordError password;



  /**
   * constructeur
   */
  public ConnexionVue() {
    this.setTitle("connexion");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 240);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    JPanel texts = new JPanel(new GridLayout(0, 1));
    JPanel labels = new JPanel(new GridLayout(0, 1));

    JLabel user = new JLabel("Nom d'utilisateur : ", JLabel.RIGHT);
    user.setPreferredSize(new Dimension(150, 0));
    user.setFont(arial);
    labels.add(user);

    JLabel pwd = new JLabel("mot de passe : ", JLabel.RIGHT);
    pwd.setPreferredSize(new Dimension(150, 0));
    pwd.setFont(arial);
    labels.add(pwd);

    username = new JPanelTextError(13);
    JPanel userNamePanel = new JPanel(new BorderLayout());
    userNamePanel.add(username);

    password = new JPanelPasswordError(13);
    JPanel passwordPanel = new JPanel(new BorderLayout());
    passwordPanel.add(password);


    texts.add(userNamePanel);
    texts.add(passwordPanel);

    this.add(texts);
    this.add(labels, BorderLayout.WEST);

    this.add(new ConnexionController(this), BorderLayout.SOUTH);
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
