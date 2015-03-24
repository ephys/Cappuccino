package paoo.cappuccino.ihm.newContact;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * View for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewContactView extends JPanel implements ChangeListener {

  private NewContactModel model;
  private ErrorableTextField contactLastNamePanel;
  private ErrorableTextField contactPhonePanel;
  private ErrorableTextField contactMailPanel;
  private ErrorableTextField contactFirstNamePanel;



  /**
   * @param model2
   * @param contactFirstNameField
   * @param contactLastNameField
   * @param contactMailField
   * @param contactPhoneField
   */
  public NewContactView(NewContactModel model,
      JTextField contactFirstNameField, JTextField contactLastNameField,
      JTextField contactMailField, JTextField contactPhoneField,
      JComboBox<ICompanyDto> comboCompanies) {
    super(new GridLayout(5, 1));
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0,
        IhmConstants.XL_GAP));

    this.model = model;

    this.contactLastNamePanel =
        new ErrorableTextField(contactLastNameField, "Nom", 16);
    this.add(this.contactLastNamePanel);

    this.contactFirstNamePanel =
        new ErrorableTextField(contactFirstNameField, "Prénom", 16);
    this.add(this.contactFirstNamePanel);

    this.contactMailPanel =
        new ErrorableTextField(contactMailField, " Mail", 16);
    this.add(this.contactMailPanel);

    this.contactPhonePanel =
        new ErrorableTextField(contactPhoneField, "Téléphone", 16);
    this.add(this.contactPhonePanel);

    JLabelFont companiesLabel = new JLabelFont("Entreprise", 16);
    JPanel panelCompanies = new JPanel(new GridLayout(2, 2));
    panelCompanies.add(companiesLabel);
    panelCompanies.add(comboCompanies);
    panelCompanies.add(new JLabel());// TODO trouver alternative


    this.add(panelCompanies);


    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    contactLastNamePanel.setError(model.getLastNameError());
    contactFirstNamePanel.setError(model.getFirstNameError());
    contactMailPanel.setError(model.getMailError());
    contactPhonePanel.setError(model.getPhoneError());
  }
}
