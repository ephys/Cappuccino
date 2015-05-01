package paoo.cappuccino.ihm.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.core.IDefaultButtonHandler;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuFrame;
import paoo.cappuccino.ihm.registration.RegistrationFrame;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class LoginViewController extends JPanel implements IDefaultButtonHandler {

  private static final long serialVersionUID = 3071496812344175953L;
  private final LoginModel model;
  private final IUserUcc userUcc;
  private final JButton loginButton;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   * @param guiManager The manager responsible for the containing frame.
   */
  public LoginViewController(LoginModel model, IGuiManager guiManager, IUserUcc userUcc) {
    super(new BorderLayout());
    this.model = model;
    this.userUcc = userUcc;

    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
        new Color(80, 80, 80)), new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP)));

    JPanel titlePanel = new JPanel(new BorderLayout());
    titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, IhmConstants.L_GAP, 0));

    JLabel logo =
        new JLabel(
            new ImageIcon(guiManager.getResourceManager().fetchImage(IhmConstants.PATH_LOGO)));
    logo.setBorder(BorderFactory.createEmptyBorder(0, 0, IhmConstants.M_GAP, 0));

    titlePanel.add(logo, BorderLayout.CENTER);
    JLabel titleLabel = new JLabelFont("Connexion", 20);
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    titlePanel.add(titleLabel, BorderLayout.SOUTH);

    this.add(titlePanel, BorderLayout.NORTH);

    // buttons //
    JPanel controls = new JPanel(new GridLayout(2, 0, 0, IhmConstants.M_GAP));

    controls.setLayout(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton registerButton = new JButton("S'inscrire");
    registerButton.addActionListener(e -> guiManager.openFrame(RegistrationFrame.class));

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    this.loginButton = new JButton("Se connecter");
    loginButton.addActionListener(e -> {
      attemptLogin(usernameField.getText(), passwordField.getPassword(), guiManager);


    });

    controls.add(registerButton);
    controls.add(loginButton);

    this.add(controls, BorderLayout.SOUTH);

    // end buttons //

    this.add(new LoginView(model, usernameField, passwordField), BorderLayout.CENTER);
  }

  /**
   * Tries to log the user in.
   *
   * @param username The user's username.
   * @param password The user's password.
   * @param guiManager The guiManager(for logs)
   */
  private void attemptLogin(String username, char[] password, IGuiManager guiManager) {
    model.resetErrors();

    boolean isValid = true;
    if (StringUtils.isEmpty(username)) {
      model.setUsernameError(IhmConstants.ERROR_FIELD_EMPTY);
      isValid = false;
    }

    if (password.length == 0) {
      model.setPasswordError(IhmConstants.ERROR_FIELD_EMPTY);
      isValid = false;
    }

    if (!isValid) {
      return;
    }

    guiManager.invoke(() -> {
      model.setUsernameError("Chargement...");
      model.setPasswordError(null);

      IUserDto user = userUcc.logIn(username, password);

      if (user == null) {
        model.setUsernameError(IhmConstants.ERROR_WRONG_LOGIN);
        model.setPasswordError(IhmConstants.ERROR_WRONG_LOGIN);

        return;
      }

      // avoid password release in case of memory dump.
        StringUtils.clearString(password);
        guiManager.getLogger().info("[Login Frame] " + user.getUsername() + " -> connexion");
        SwingUtilities.invokeLater(() -> guiManager.openFrame(MenuFrame.class).setLoggedUser(user));
      });
  }

  @Override
  public JButton getSubmitButton() {
    return loginButton;
  }
}
