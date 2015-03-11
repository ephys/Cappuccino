package paoo.cappuccino.ihm.login;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * View for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class LoginView extends JPanel implements ChangeListener {


  private static final long serialVersionUID = -5524493008076142378L;

  private LoginModel model;

  private ErrorableTextField usernamePanel;
  private ErrorableTextField passwordPanel;

  /**
   * Creates a new login view.
   *
   * @param model The view's model.
   * @param usernameField A text field for the username input.
   * @param passwordField A password field for the password input.
   */
  public LoginView(LoginModel model, JTextField usernameField, JPasswordField passwordField) {
    super(new GridLayout(2, 1, 0, IhmConstants.M_GAP));

    this.model = model;

    this.usernamePanel = new ErrorableTextField(usernameField, "Nom d'utilisateur");
    this.add(this.usernamePanel);

    this.passwordPanel = new ErrorableTextField(passwordField, "Mot de passe");
    this.add(this.passwordPanel);

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    usernamePanel.setError(model.getUsernameError());
    passwordPanel.setError(model.getPasswordError());
  }
}
