package paoo.cappuccino.ihm.newCompany;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * ViewController for the registration Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class NewCompanyViewController extends JPanel {

  /**
   * Creates a new ViewController for the new company gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   */
  public NewCompanyViewController(NewCompanyModel model, MenuModel menu, IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0, IhmConstants.M_GAP));

    JTextField companyNameField = new JTextField();
    JTextField streetField = new JTextField();
    JTextField cityField = new JTextField();
    JTextField boxField = new JTextField();
    JTextField numerField = new JTextField();
    JTextField postCodeField = new JTextField();


    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton createButton = new JButton("Créer");
    createButton.addActionListener(e -> {
      if (companyNameField.getText().isEmpty())
        model.setCompanyNameError(IhmConstants.ERROR_FIELD_EMPTY);
      // test input
        JOptionPane.showMessageDialog(this, "entreprise crée");
      });


    controls.add(createButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new NewCompanyView(model, companyNameField, numerField, postCodeField, streetField,
        cityField, boxField));
  }
}
