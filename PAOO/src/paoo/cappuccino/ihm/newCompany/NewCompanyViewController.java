package paoo.cappuccino.ihm.newCompany;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewCompanyViewController extends JPanel {

  /**
   * Creates a new ViewController for the new company gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   */
  public NewCompanyViewController(NewCompanyModel model, MenuModel menu,
      IGuiManager manager) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP,
        0, IhmConstants.M_GAP));

    JTextField companyNameField = new JTextField();
    JTextField streetField = new JTextField();
    JTextField cityField = new JTextField();
    JTextField boxField = new JTextField();
    JTextField numerField = new JTextField();
    JTextField postCodeField = new JTextField();


    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP,
            IhmConstants.M_GAP));

    JButton createButton = new JButton("Créer");
    createButton
        .addActionListener(e -> {
          // test input
          model.setCompanyNameError(StringUtils.isEmpty(companyNameField
              .getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : (!StringUtils.isAlphaString(companyNameField.getText()) ? IhmConstants.ERROR_ALPHA_INPUT
                  : null));

          model.setStreetError(StringUtils.isEmpty(streetField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : null);

          model.setCityError(StringUtils.isEmpty(cityField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : null);

          model.setBoxError(StringUtils.isEmpty(boxField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : (!StringUtils.isAlphaString(boxField.getText()) ? IhmConstants.ERROR_ALPHA_INPUT
                  : null));

          model.setNumerError(StringUtils.isEmpty(numerField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : null);

          model.setPostCodeError(StringUtils.isEmpty(postCodeField
              .getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null);
          if (!model.hasError()) {
            // TODO ucc.createCompany(mlkqdsjfqmlksdjf);
            // TODO check nom non encore utiliser

            model.clearError();
            JOptionPane.showMessageDialog(null, "Entreprise créée");

            // clear les champs
            companyNameField.setText(null);
            streetField.setText(null);
            cityField.setText(null);
            boxField.setText(null);
            numerField.setText(null);
            postCodeField.setText(null);

            // redirection utilisateur
            menu.setCurrentPage(MenuEntry.CREATE_CONTACT);
          }
        });


    controls.add(createButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new NewCompanyView(model, companyNameField, numerField,
        postCodeField, streetField, cityField, boxField));
  }
}
