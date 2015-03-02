package paoo.cappuccino.ihm.utils;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * classe d'un label + erreure possible en dessous
 *
 * @author mopsome
 *
 */
public class JPanelTextError extends JPanel {
	private JTextField input;
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
	public JPanelTextError(String description, String exemple) {
		this.setLayout(new BorderLayout());
		this.input = new JTextField(exemple, 13);
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
	public JPanelTextError(String label) {
		this(label, "");
	}

	/**
	 * Get Input textarea
	 * 
	 * @return String input
	 */
	public String getInput() {
		return input.getText();
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
