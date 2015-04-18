package paoo.cappuccino.ihm.newcontact;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.newcompany.NewCompanyView;
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

  private final NewContactModel model;
  private final ErrorableTextField contactLastNamePanel;
  private final ErrorableTextField contactMailPanel;
  private final ErrorableTextField contactFirstNamePanel;
  private final JLabel companyError;

  /**
   * Creates a new view for the contact creation screen.
   */
  public NewContactView(NewContactModel model, JTextField contactFirstNameField,
                        JTextField contactLastNameField, JTextField contactMailField,
                        JTextField contactPhoneField, JComboBox<ICompanyDto> comboCompanies) {
    super(new GridBagLayout());
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;

    this.contactLastNamePanel = new ErrorableTextField(contactLastNameField, "Nom", 16);
    this.add(this.contactLastNamePanel, NewCompanyView.makeGbc(0));

    this.contactFirstNamePanel = new ErrorableTextField(contactFirstNameField, "Prénom", 16);
    this.add(this.contactFirstNamePanel, NewCompanyView.makeGbc(1));

    this.contactMailPanel = new ErrorableTextField(contactMailField, " Mail", 16);
    this.add(this.contactMailPanel, NewCompanyView.makeGbc(2));

    this.add(new ErrorableTextField(contactPhoneField, "Téléphone", 16), NewCompanyView.makeGbc(3));

    JLabelFont companiesLabel = new JLabelFont("Entreprise", 16);
    JPanel panelCompanies = new JPanel(new GridLayout(2, 2));
    panelCompanies.add(companiesLabel);
    panelCompanies.add(comboCompanies);
    panelCompanies.add(new JPanel());

    this.companyError = new JLabel();
    companyError.setForeground(Color.RED);
    panelCompanies.add(companyError);

    this.add(panelCompanies, NewCompanyView.makeGbc(4));

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    contactLastNamePanel.setError(model.getLastNameError());
    contactFirstNamePanel.setError(model.getFirstNameError());
    contactMailPanel.setError(model.getMailError());
    companyError.setText(model.getCompanyError());
  }
}
