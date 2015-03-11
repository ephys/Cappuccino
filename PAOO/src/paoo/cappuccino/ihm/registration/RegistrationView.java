package paoo.cappuccino.ihm.registration;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * View for the registration Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class RegistrationView extends JPanel implements ChangeListener {

  private RegistrationModel model;
  private ErrorableTextField usernamePanel;
  private ErrorableTextField passwordPanel;
  private ErrorableTextField confirmPasswordPanel;
  private ErrorableTextField lastNamePanel;
  private ErrorableTextField firstNamePanel;
  private ErrorableTextField emailPanel;


  /**
   * Creates a new registration view.
   *
   * @param model The view's model.
   * @param usernameField A text field for the username input.
   * @param passwordField A password field for the password input.
   * @param confirmPasswordField A password field for the password confirmation input.
   * @param lastNameField A text field for the last name input.
   * @param firstNameField A text field for the first name input.
   * @param emailField A text field for the email input.
   */
  public RegistrationView(RegistrationModel model,
      JTextField usernameField, JPasswordField passwordField,
      JPasswordField confirmPasswordField, JTextField lastNameField,
      JTextField firstNameField, JTextField emailField) {
    super(new GridLayout(6, 1, 0, IhmConstants.M_GAP));

    this.model = model;

    this.usernamePanel =
        new ErrorableTextField(usernameField, "Nom d'utilisateur");
    this.add(this.usernamePanel);

    this.passwordPanel =
        new ErrorableTextField(passwordField, "Mot de passe");
    this.add(this.passwordPanel);

    this.confirmPasswordPanel =
        new ErrorableTextField(confirmPasswordField,
            "Confirmer mot de passe");
    this.add(this.confirmPasswordPanel);

    this.lastNamePanel = new ErrorableTextField(lastNameField, "Nom");
    this.add(this.lastNamePanel);

    this.firstNamePanel = new ErrorableTextField(firstNameField, "Prenom");
    this.add(this.firstNamePanel);

    this.emailPanel = new ErrorableTextField(emailField, "Email");
    this.add(this.emailPanel);

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    usernamePanel.setError(model.getUsernameError());
    passwordPanel.setError(model.getPasswordError());
    confirmPasswordPanel.setError(model.getConfirmPasswordError());
    lastNamePanel.setError(model.getlastNameError());
    firstNamePanel.setError(model.getfirstNameError());
    emailPanel.setError(model.getemailError());
  }
}
