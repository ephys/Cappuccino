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
 * 
 * @author Opsomer Mathias
 *
 */
public class ConnexionVue extends JFrame {
  private Font arial = new Font("Arial", Font.PLAIN, 16);

  public ConnexionVue() {
    this.setTitle("connexion");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 240);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    JPanel texts = new JPanel(new GridLayout(0, 1));
    JPanel labels = new JPanel(new GridLayout(0, 1));

    JLabel user = new JLabel("Nom d'utilisateur : ", JLabel.RIGHT);
    user.setPreferredSize(new Dimension(150, 0));
    labels.add(user);
    user.setFont(arial);
    JLabel pwd = new JLabel("Mot de passe : ", JLabel.RIGHT);
    pwd.setPreferredSize(new Dimension(150, 0));
    pwd.setFont(arial);
    labels.add(pwd);

    JPanelTextError username = new JPanelTextError("jojo", 10);
    username.setError("example erreure");
    JPanel userNamePanel = new JPanel();
    userNamePanel.add(username);

    JPanelPasswordError password = new JPanelPasswordError("jojo", 10);
    password.setError("example erreure");
    JPanel passwordPanel = new JPanel();
    passwordPanel.add(password);

    texts.add(userNamePanel);
    texts.add(passwordPanel);

    this.add(texts);
    this.add(labels, BorderLayout.WEST);
    this.add(new ConnexionController(), BorderLayout.SOUTH);
    this.setVisible(true);
  }

  /**
   * classe interne pour le password field + erreure
   * 
   * @author mopsome
   *
   */
  class JPanelPasswordError extends JPanel {
    private JPasswordField password;
    private JLabel error;

    public JPanelPasswordError(String string, int i) {
      this.setLayout(new GridLayout(0, 1));
      password = new JPasswordField(string, i);
      error = new JLabel();
      error.setForeground(Color.RED);
      this.add(password);
      this.add(error);

    }

    public char[] getPassword() {
      return password.getPassword();
    }

    public String getError() {
      return error.getText();
    }

    public void setError(String error) {
      this.error.setText(error);
    }
  }
}
