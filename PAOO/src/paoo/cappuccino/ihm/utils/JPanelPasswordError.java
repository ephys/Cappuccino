/**
 *
 */
package paoo.cappuccino.ihm.utils;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class JPanelPasswordError extends JPanel {
	private JPasswordField input;
	private JLabel error;
	private JLabel label;

	/**
	 * constructor
	 *
	 * @param String
	 *            the textarea's template
	 * @param String
	 *            description textarea
	 */
	public JPanelPasswordError(String description, String exemple) {
		this.setLayout(new BorderLayout());
		this.input = new JPasswordField(exemple, 13);
		this.label = new JLabel(description + " : ");
		this.error = new JLabel();
		this.add(input);
		this.add(label, BorderLayout.WEST);// ?? definir taille label
		this.add(error, BorderLayout.SOUTH);
	}

	/**
	 * constructor only with label
	 *
	 * @param label
	 *            description textarea
	 */
	public JPanelPasswordError(String label) {
		this(label, "");
	}

	/**
	 * Get Input passwordField
	 * 
	 * @return char[] input
	 */
	public char[] getInput() {
		return input.getPassword();
	}

	/**
	 * setError
	 * 
	 * @param String
	 *            new error
	 */
	public void setError(String error) {
		this.error.setText(error);
	}

}