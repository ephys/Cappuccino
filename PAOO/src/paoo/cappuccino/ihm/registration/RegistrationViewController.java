package paoo.cappuccino.ihm.registration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.menu.MenuFrame;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * ViewController for the registration Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class RegistrationViewController extends JPanel {

  /**
   * Creates a new ViewController for the registration gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   */
  public RegistrationViewController(RegistrationModel model, IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
        new Color(80, 80, 80)), new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP)));

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JTextField lastNameField = new JTextField();
    JTextField firstNameField = new JTextField();
    JTextField emailField = new JTextField();

    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton validateButton = new JButton("Valider");
    validateButton.addActionListener(e -> {
      IUserDto user =
          model.attemptRegistration(usernameField.getText(), passwordField.getPassword(),
              confirmPasswordField.getPassword(), lastNameField.getText(),
              firstNameField.getText(), emailField.getText());
      if (user != null) {
        manager.openFrame(MenuFrame.class).setLoggedUser(user);
      }
    });

    JButton cancelButton = new JButton("Annuler");
    cancelButton.addActionListener(e -> manager.openFrame(LoginFrame.class));


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
}
