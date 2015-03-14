package paoo.cappuccino.ihm.registration;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * ViewController for the registration Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class RegistrationViewController extends JPanel{

	  /**
	   * Creates a new ViewController for the registration gui.
	   * @param model The ViewController's model.
	   * @param manager The manager responsible for the opening/closing this frame.
	   */
	  public RegistrationViewController(RegistrationModel model, IGuiManager manager) {
	    super(new BorderLayout());
	    this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
	                                                   IhmConstants.M_GAP));

	    JTextField usernameField = new JTextField();
	    JPasswordField passwordField = new JPasswordField();
	    JPasswordField confirmPasswordField = new JPasswordField();
	    JTextField lastNameField = new JTextField();
	    JTextField firstNameField = new JTextField();
	    JTextField emailField = new JTextField();

	    JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

	    JButton validateButton = new JButton("Valider");
	    validateButton.addActionListener(e -> {
	    if (model.attemptRegistration(usernameField.getText(), passwordField.getPassword(),
					  confirmPasswordField.getPassword(),
					  lastNameField.getText(),
					  firstNameField.getText(), emailField.getText())) {
		        manager.openFrame(LoginFrame.class);
		        // guiManager.openFrame();
		      }
	    });

	    JButton cancelButton = new JButton("Annuler");
	    cancelButton.addActionListener(e -> {
	        manager.openFrame(LoginFrame.class);
	        // guiManager.openFrame();

	    });

	    controls.add(validateButton);
	    controls.add(cancelButton);

	    this.add(controls, BorderLayout.SOUTH);
	    // end buttons //

	    this.add(new RegistrationView(model, usernameField, passwordField,confirmPasswordField,lastNameField,firstNameField,emailField));
	  }

}
