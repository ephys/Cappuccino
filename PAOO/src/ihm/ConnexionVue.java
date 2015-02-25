package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

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

    username = new JPanelTextError("jojo");
    JPanel userNamePanel = new JPanel(new BorderLayout());
    userNamePanel.add(username, BorderLayout.SOUTH);

    password = new JPanelPasswordError("jojo");
    JPanel passwordPanel = new JPanel(new BorderLayout());
    passwordPanel.add(password, BorderLayout.SOUTH);


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
   * classe interne pour le password field + erreure
   * 
   * @author Opsomer Mathias
   *
   */
  class JPanelPasswordError extends JPanel {
    private JPasswordField password;
    private JLabel error;

    public JPanelPasswordError(String string) {
      this.setLayout(new GridLayout(0, 1));
      password = new JPasswordField(string);
      error = new JLabel();
      error.setForeground(Color.RED);
      this.add(password);
      this.add(error);

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

}
