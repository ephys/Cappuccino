package paoo.cappuccino.ihm.login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.IhmConstants;

public class LoginViewController extends JPanel {

  public LoginViewController(LoginModel model, IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP));

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    // buttons //
    JPanel controls = new JPanel(new GridLayout(2, 0, 0, IhmConstants.M_GAP));

    controls.setLayout(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton registerButton = new JButton("S'inscrire");
    registerButton.addActionListener(e -> {
      throw new UnsupportedOperationException("Not yet implemented.");
      // guiManager.openFrame(null);
      });

    JButton loginButton = new JButton("Se connecter");
    loginButton.addActionListener(e -> {
      if (model.attemptLogin(usernameField.getText(), passwordField.getPassword())) {
        throw new UnsupportedOperationException("Login success but the following "
            + "is not yet implemented");
        // guiManager.openFrame();
      }
    });

    controls.add(registerButton);
    controls.add(loginButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new LoginView(model, usernameField, passwordField), BorderLayout.CENTER);
  }
}
