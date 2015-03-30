package paoo.cappuccino.ihm.newcompany;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.ICompanyUcc;
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
   * @param companyUcc
   */
  public NewCompanyViewController(NewCompanyModel model, MenuModel menu, IGuiManager manager,
      ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0, IhmConstants.M_GAP));
    // Log
    manager.getLogger().info("NewCompany Frame");

    JTextField companyNameField = new JTextField();
    JTextField streetField = new JTextField();
    JTextField cityField = new JTextField();
    JTextField boxField = new JTextField();
    JTextField numerField = new JTextField();
    JTextField postCodeField = new JTextField();


    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton createButton = new JButton("Créer");
    createButton
    .addActionListener(e -> {
      // test input
      model.setCompanyNameError(StringUtils.isEmpty(companyNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
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

      model.setPostCodeError(StringUtils.isEmpty(postCodeField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : null);
      if (!model.hasError()) {
        try {
          ICompanyDto company =
              companyUcc.create(menu.getLoggedUser(), companyNameField.getText(),
                      streetField.getText(), numerField.getText(), boxField.getText(),
                  postCodeField.getText(), cityField.getText());


          model.clearError();
          manager.getLogger().info("new Company created : " + company.getName());
          JOptionPane.showMessageDialog(null, "Entreprise créée");

          // clear les champs
          companyNameField.setText(null);
          streetField.setText(null);
          cityField.setText(null);
          boxField.setText(null);
          numerField.setText(null);
          postCodeField.setText(null);

          // redirection utilisateur
          menu.setTransitionObject(company);
          menu.setCurrentPage(MenuEntry.CREATE_CONTACT);
        } catch (IllegalArgumentException ex) {
          model.setNameError(IhmConstants.NAME_ALREADY_TAKEN);
        }
      }
    });


    controls.add(createButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new NewCompanyView(model, companyNameField, numerField, postCodeField, streetField,
        cityField, boxField));
  }
}
