package paoo.cappuccino.ihm.login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuFrame;
import paoo.cappuccino.ihm.registration.RegistrationFrame;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class LoginViewController extends JPanel {


  private static final long serialVersionUID = 3071496812344175953L;

  /**
   * Creates a new ViewController for the Login gui.
   * 
   * @param model The ViewController's model.
   * @param manager The manager responsible for the containing frame.
   */
  public LoginViewController(LoginModel model, IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP,
        IhmConstants.M_GAP, 0, IhmConstants.M_GAP));

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    // buttons //
    JPanel controls =
        new JPanel(new GridLayout(2, 0, 0, IhmConstants.M_GAP));

    controls.setLayout(new FlowLayout(FlowLayout.RIGHT,
        IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton registerButton = new JButton("S'inscrire");
    registerButton.addActionListener(e -> {
      manager.openFrame(RegistrationFrame.class);
    });

    JButton loginButton = new JButton("Se connecter");
    loginButton.addActionListener(e -> {
      IUserDto user = null;
      user =
          model.attemptLogin(usernameField.getText(),
              passwordField.getPassword());
      if (user != null) {
        manager.openFrame(MenuFrame.class, user);
      }
    });

    controls.add(registerButton);
    controls.add(loginButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new LoginView(model, usernameField, passwordField));
  }
}
