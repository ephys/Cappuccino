package paoo.cappuccino.ihm.registration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.core.IDefaultButtonHandler;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.menu.MenuFrame;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the registration Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class RegistrationViewController extends JPanel implements IDefaultButtonHandler {

  private final RegistrationModel model;
  private final IUserUcc userUcc;
  private final JButton validateButton;

  /**
   * Creates a new ViewController for the registration gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param userUcc The app instance of the user use case controller.
   */
  public RegistrationViewController(RegistrationModel model,
      IGuiManager manager, IUserUcc userUcc) {
    super(new BorderLayout());
    this.model = model;
    this.userUcc = userUcc;

    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
        new Color(80, 80, 80)), new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP)));

    // log
    manager.getLogger().info("Registration Frame");

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JTextField lastNameField = new JTextField();
    JTextField firstNameField = new JTextField();
    JTextField emailField = new JTextField();

    validateButton = new JButton("Valider");
    validateButton.addActionListener(e -> {
      IUserDto user =
          attemptRegistration(usernameField.getText(), passwordField.getPassword(),
              confirmPasswordField.getPassword(), lastNameField.getText(),
              firstNameField.getText(), emailField.getText());
      if (user != null) {
        manager.openFrame(MenuFrame.class).setLoggedUser(user);
      }
    });

    JButton cancelButton = new JButton("Annuler");
    cancelButton.addActionListener(e -> manager.openFrame(LoginFrame.class));

    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    controls.add(cancelButton);
    controls.add(validateButton);

    JLabel titleLabel = new JLabelFont("Inscription", 20);
    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, IhmConstants.L_GAP, 0));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    this.add(titleLabel, BorderLayout.NORTH);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new RegistrationView(model, usernameField, passwordField, confirmPasswordField,
        lastNameField, firstNameField, emailField));
  }

  /**
   * Tries to register a user.
   *
   * @return a user or null if registration failed
   */
  private IUserDto attemptRegistration(String username, char[] password, char[] confirmPassword,
      String lastName, String firstName, String email) {

    String usernameError =
        StringUtils.isEmpty(username) ? IhmConstants.ERROR_FIELD_EMPTY : (!StringUtils
            .isAlphaString(username) ? IhmConstants.ERROR_ALPHA_INPUT : null);

    String lastNameError = StringUtils.isEmpty(lastName) ? IhmConstants.ERROR_FIELD_EMPTY : null;

    String firstNameError = StringUtils.isEmpty(firstName) ? IhmConstants.ERROR_FIELD_EMPTY : null;

    String emailError =
        StringUtils.isEmpty(email) ? IhmConstants.ERROR_FIELD_EMPTY
            : (!StringUtils.isEmail(email) ? IhmConstants.ERROR_INVALID_EMAIL : null);

    String passwordError =
        !StringUtils.isValidPassword(password) ? IhmConstants.ERROR_INVALID_PASSWORD : null;

    String confirmPasswordError =
        (!Arrays.equals(password, confirmPassword) ? IhmConstants.ERROR_NOT_MATCHING_PASSWORD
            : null);

    model.setErrors(passwordError, usernameError, confirmPasswordError, lastNameError,
        firstNameError, emailError);

    if (usernameError != null || lastNameError != null || firstNameError != null
        || emailError != null || passwordError != null || confirmPasswordError != null) {
      return null;
    }

    IUserDto user = userUcc.register(username, password, firstName, lastName, email);

    StringUtils.clearString(password);
    StringUtils.clearString(confirmPassword);

    return user;
  }

  @Override
  public JButton getSubmitButton() {
    return validateButton;
  }
}
