package paoo.cappuccino.ihm.newcontact;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.util.StringUtils;

/**
 * ViewController for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewContactViewController extends JPanel {

  /**
   * Creates a new ViewController for the new company gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param contactUcc
   * @param companyUcc
   */
  public NewContactViewController(NewContactModel model, MenuModel menu, IGuiManager manager,
      IContactUcc contactUcc, ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0, IhmConstants.M_GAP));

    // log
    manager.getLogger().info("NewContact Frame");

    JTextField contactFirstNameField = new JTextField();
    JTextField contactLastNameField = new JTextField();
    JTextField contactMailField = new JTextField();
    JTextField contactPhoneField = new JTextField();

    JComboBox<ICompanyDto> comboCompanies =
        new JComboBox<ICompanyDto>(companyUcc.getAllCompanies());
    comboCompanies.setRenderer(new ComboEntrepriseRenderer());


    if (menu.hasTransitionObject())
      comboCompanies.setSelectedItem(menu.getTransitionObject());

    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));

    JButton createButton = new JButton("Créer");
    createButton
    .addActionListener(e -> {
      // test input
      model.setFirstNameError(StringUtils.isEmpty(contactFirstNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : (!StringUtils.isAlphaString(contactFirstNameField.getText()) ? IhmConstants.ERROR_ALPHA_INPUT
                  : null));

      model.setLastNameError(StringUtils.isEmpty(contactLastNameField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : (!StringUtils.isAlphaString(contactLastNameField.getText()) ? IhmConstants.ERROR_ALPHA_INPUT
                  : null));

      model.setMailError(StringUtils.isEmpty(contactMailField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : (!StringUtils.isEmail(contactMailField.getText()) ? IhmConstants.ERROR_INVALID_EMAIL
                  : null));

      model.setPhoneError(StringUtils.isEmpty(contactPhoneField.getText()) ? IhmConstants.ERROR_FIELD_EMPTY
              : null);


      if (!model.hasError()) {
        IContactDto contact =
            contactUcc.create(((ICompanyDto) comboCompanies.getSelectedItem()).getId(),
                    contactMailField.getText(), contactFirstNameField.getText(),
                contactLastNameField.getText(), contactPhoneField.getText());

        if (contact != null) {
          model.clearError();
          manager.getLogger().info(
              "new Contact created : " + contact.getFirstName() + " " + contact.getLastName()
                      + "  ( " + ((ICompanyDto) comboCompanies.getSelectedItem()).getName() + " )");
          JOptionPane.showMessageDialog(null, "Contact créer");

          // clear les champs
          contactFirstNameField.setText(null);
          contactLastNameField.setText(null);
          contactMailField.setText(null);
          contactPhoneField.setText(null);
        } else {
          JOptionPane.showMessageDialog(null,
                  "Erreure survenue lors de la création du contact. Veuillez réessayer.");
        }
      }

    });


    controls.add(createButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new NewContactView(model, contactFirstNameField, contactLastNameField,
        contactMailField, contactPhoneField, comboCompanies));
  }

  class ComboEntrepriseRenderer implements ListCellRenderer<ICompanyDto> {

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList,
     * java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends ICompanyDto> arg0,
        ICompanyDto value, int arg2, boolean arg3, boolean arg4) {
      if (value == null)
        return new JLabel();
      return new JLabel(value.getName());
    }
  }
}
