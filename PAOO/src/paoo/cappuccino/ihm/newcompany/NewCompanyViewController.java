package paoo.cappuccino.ihm.newcompany;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IDefaultButtonHandler;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the new company Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewCompanyViewController extends JPanel implements IDefaultButtonHandler {
  private final JButton createButton;

  /**
   * Creates a new ViewController for the new company gui.
   */
  public NewCompanyViewController(NewCompanyModel model, MenuModel menu, IGuiManager manager,
      ICompanyUcc companyUcc) {
    super(new GridBagLayout());

    final JPanel wrapperPanel = new JPanel(new BorderLayout());
    this.add(wrapperPanel, new GridBagConstraints());
    wrapperPanel.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP));

    JTextField companyNameField = new JTextField();
    JTextField streetField = new JTextField();
    JTextField cityField = new JTextField();
    JTextField boxField = new JTextField();
    JTextField numField = new JTextField();
    JTextField postCodeField = new JTextField();
    wrapperPanel.add(new NewCompanyView(model, companyNameField, numField, postCodeField,
        streetField, cityField, boxField));

    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));
    wrapperPanel.add(controls, BorderLayout.SOUTH);

    createButton = new JButton("Créer");
    controls.add(createButton);
    createButton.addActionListener(e -> {
      // test input
        String nameError =
            StringUtils.isEmpty(companyNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null;

        String streetError =
            StringUtils.isEmpty(streetField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null;

        String cityError =
            StringUtils.isEmpty(cityField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null;

        String boxError =
            StringUtils.isEmpty(boxField.getText()) ? null : (StringUtils.isAlphanumeric(boxField
                .getText()) ? null : IhmConstants.ERROR_ALPHANUM_INPUT);

        String numError =
            StringUtils.isEmpty(numField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null;

        String postCodeError =
            StringUtils.isEmpty(postCodeField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
                : ((!StringUtils.isNumeric(postCodeField.getText()) || postCodeField.getText()
                    .length() != 4) ? "Le code postal doit être composé de 4 chiffres" : null);

        model.setErrors(nameError, streetError, cityError, boxError, numError, postCodeError);
        if (model.hasError()) {
          return;
        }

        try {
          final ICompanyDto company =
              companyUcc.create(menu.getLoggedUser(), companyNameField.getText(),
                  streetField.getText(), numField.getText(), boxField.getText(),
                  postCodeField.getText(), cityField.getText());

          manager.getLogger().info("new Company created : " + company.getName());
          JOptionPane.showMessageDialog(this, "Entreprise créée");

          menu.setCurrentPage(MenuEntry.CREATE_CONTACT, company);
        } catch (IllegalArgumentException ex) {
          model.setErrors(IhmConstants.NAME_ALREADY_TAKEN, null, null, null, null, null);
        }
      });
  }

  @Override
  public JButton getSubmitButton() {
    return createButton;
  }
}
