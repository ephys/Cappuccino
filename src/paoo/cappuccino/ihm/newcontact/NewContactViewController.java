package paoo.cappuccino.ihm.newcontact;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.IDefaultButtonHandler;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.CompanyListRenderer;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the new contact Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewContactViewController extends JPanel implements IDefaultButtonHandler {

  private final JComboBox<ICompanyDto> companiesCombo;
  private final NewContactModel model;
  private final JTextField contactFirstNameField = new JTextField();
  private final JTextField contactLastNameField = new JTextField();
  private final JTextField contactMailField = new JTextField();
  private final JTextField contactPhoneField = new JTextField();
  private final JButton modifyContactButton;


  /**
   * Creates a new ViewController for the new contact gui.
   *
   * @param model The ViewController's model.
   */
  public NewContactViewController(NewContactModel model, IContactUcc contactUcc,
      ICompanyUcc companyUcc, MenuModel menu) {
    super(new GridBagLayout());
    this.model = model;
    final JPanel wrapperPanel = new JPanel(new BorderLayout());
    this.add(wrapperPanel);
    wrapperPanel.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP));

    final List<ICompanyDto> companies = companyUcc.getAllCompanies();
    this.companiesCombo = new JComboBox<>(companies.toArray(new ICompanyDto[companies.size()]));

    // check if modifying contact
    final IContactDto contactToModify = model.getContact();
    final boolean editionMode = contactToModify != null;
    if (editionMode) {
      contactFirstNameField.setText(contactToModify.getFirstName());
      contactLastNameField.setText(contactToModify.getLastName());
      contactMailField.setText(contactToModify.getEmail());
      contactPhoneField.setText(contactToModify.getPhone());
      companiesCombo.setSelectedItem(companyUcc.getCompanyById(contactToModify.getCompany()));
      model.setErrors(null, null, null);
    }

    if (model.getCompany() != null) {
      companiesCombo.setSelectedItem(model.getCompany());
    }

    wrapperPanel.add(new NewContactView(model, contactFirstNameField, contactLastNameField,
        contactMailField, contactPhoneField, companiesCombo));

    companiesCombo.setRenderer(new CompanyListRenderer());

    final JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));
    wrapperPanel.add(controls, BorderLayout.SOUTH);

    this.modifyContactButton = new JButton(editionMode ? "Modifier" : "Créer");
    controls.add(modifyContactButton);

    modifyContactButton.addActionListener(e -> {
      if (hasError()) {
        return;
      }

      ICompanyDto company = (ICompanyDto) companiesCombo.getSelectedItem();
      if (company == null) {
        return;
      }

      IContactDto contact;
      if (editionMode) {
        contact =
            contactUcc.update(contactToModify.getId(), company.getId(), contactMailField.getText(),
                contactFirstNameField.getText(), contactLastNameField.getText(),
                contactPhoneField.getText());
      } else {
        contact =
            contactUcc.create(company.getId(), contactMailField.getText(),
                contactFirstNameField.getText(), contactLastNameField.getText(),
                contactPhoneField.getText());
      }

      JOptionPane.showMessageDialog(this, editionMode ? "Contact modifié" : "Contact créé");

      menu.setCurrentPage(MenuEntry.CONTACT_DETAILS, contact);
    });
  }

  private boolean hasError() {
    String firstNameError =
        StringUtils.isEmpty(contactFirstNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
            : null;

    String lastNameError =
        StringUtils.isEmpty(contactLastNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY : null;

    String mailError =
        !StringUtils.isEmpty(contactMailField.getText())
            && !StringUtils.isEmail(contactMailField.getText()) ? IhmConstants.ERROR_INVALID_EMAIL
            : null;

    model.setErrors(firstNameError, lastNameError, mailError);

    return model.hasError();
  }

  @Override
  public JButton getSubmitButton() {
    return modifyContactButton;
  }
}
