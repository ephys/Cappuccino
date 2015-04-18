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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.IGuiManager;
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
public class NewContactViewController extends JPanel implements ChangeListener {

  private final JComboBox<ICompanyDto> companiesCombo;
  private final NewContactModel model;

  /**
   * Creates a new ViewController for the new contact gui.
   *
   * @param model The ViewController's model.
   */
  public NewContactViewController(NewContactModel model, IGuiManager manager,
                                  IContactUcc contactUcc, ICompanyUcc companyUcc, MenuModel menu) {
    super(new GridBagLayout());
    this.model = model;
    final JPanel wrapperPanel = new JPanel(new BorderLayout());
    this.add(wrapperPanel);
    wrapperPanel.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP,
                                           0, IhmConstants.M_GAP));

    final JTextField contactFirstNameField = new JTextField();
    final JTextField contactLastNameField = new JTextField();
    final JTextField contactMailField = new JTextField();
    final JTextField contactPhoneField = new JTextField();
    final List<ICompanyDto> companies = companyUcc.getAllCompanies();
    this.companiesCombo = new JComboBox<>(companies.toArray(new ICompanyDto[companies.size()]));

    wrapperPanel.add(new NewContactView(model, contactFirstNameField, contactLastNameField,
                                        contactMailField, contactPhoneField, companiesCombo));

    companiesCombo.setRenderer(new CompanyListRenderer());
    companiesCombo.addActionListener(
        event -> model.setCompanyDto((ICompanyDto) companiesCombo.getSelectedItem()));

    final JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP,
                                                IhmConstants.M_GAP));
    wrapperPanel.add(controls, BorderLayout.SOUTH);
    final JButton createButton = new JButton("Créer");
    controls.add(createButton);

    createButton.addActionListener(e -> {
      String firstNameError = StringUtils.isEmpty(contactFirstNameField.getText())
                              ? IhmConstants.ERROR_FIELD_EMPTY : null;

      String lastNameError = StringUtils.isEmpty(contactLastNameField.getText())
                             ? IhmConstants.ERROR_FIELD_EMPTY : null;

      String mailError = !StringUtils.isEmpty(contactMailField.getText())
                         && !StringUtils.isEmail(contactMailField.getText())
                         ? IhmConstants.ERROR_INVALID_EMAIL : null;

      ICompanyDto company = (ICompanyDto) companiesCombo.getSelectedItem();
      String companyError = company == null ? "Sélectionnez une entreprise." : null;

      model.setErrors(firstNameError, lastNameError, mailError, companyError);

      if (company == null || model.hasError()) {
        return;
      }

      IContactDto contact = contactUcc.create(company.getId(), contactMailField.getText(),
                            contactFirstNameField.getText(), contactLastNameField.getText(),
                            contactPhoneField.getText());

      manager.getLogger().info("new Contact created : "
                               + contact.getFirstName() + " "
                               + contact.getLastName()
                               + "  ("
                               + ((ICompanyDto) companiesCombo.getSelectedItem()).getName()
                               + ")");

      JOptionPane.showMessageDialog(this, "Contact créé");
      menu.setCurrentPage(MenuEntry.CONTACT_DETAILS, contact);
    });

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    if (model.getCompanyDto() == null
        || companiesCombo.getSelectedItem() != model.getCompanyDto()) {
      companiesCombo.setSelectedItem(model.getCompanyDto());
    }
  }
}
