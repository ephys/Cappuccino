package paoo.cappuccino.ihm.newCompany;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * View for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewCompanyView extends JPanel implements ChangeListener {

  private NewCompanyModel model;
  private ErrorableTextField CompanyNamePanel;
  private ErrorableTextField numerPanel;
  private ErrorableTextField postCodePanel;
  private ErrorableTextField boxPanel;
  private ErrorableTextField cityPanel;
  private ErrorableTextField streetPanel;



  public NewCompanyView(NewCompanyModel model, JTextField companyNameField, JTextField numerField,
      JTextField postCodeField, JTextField streetField, JTextField cityField, JTextField boxField) {
    super(new GridLayout(7, 1));
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;

    this.CompanyNamePanel = new ErrorableTextField(companyNameField, "Nom", 16);
    this.add(this.CompanyNamePanel);

    this.add(new JLabelFont("Adresse", 16));

    this.streetPanel = new ErrorableTextField(streetField, "Rue", 16);
    this.add(this.streetPanel);

    this.cityPanel = new ErrorableTextField(cityField, "Ville", 16);
    this.add(this.cityPanel);

    this.boxPanel = new ErrorableTextField(boxField, "Boite", 16);
    this.add(this.boxPanel);

    this.numerPanel = new ErrorableTextField(numerField, "Numéro", 16);
    this.add(this.numerPanel);

    this.postCodePanel = new ErrorableTextField(postCodeField, "Code postal", 16);
    this.add(this.postCodePanel);



    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    CompanyNamePanel.setError(model.getCompanyNameError());
    streetPanel.setError(model.getStreetError());
    cityPanel.setError(model.getCityError());
    boxPanel.setError(model.getBoxError());
    numerPanel.setError(model.getNumerError());
    postCodePanel.setError(model.getPostCodeError());

  }
}
