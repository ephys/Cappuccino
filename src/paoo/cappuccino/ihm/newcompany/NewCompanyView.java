package paoo.cappuccino.ihm.newcompany;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.ErrorableTextField;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * View for the new company Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewCompanyView extends JPanel implements ChangeListener {

  private final NewCompanyModel model;
  private final ErrorableTextField companyNamePanel;
  private final ErrorableTextField numPanel;
  private final ErrorableTextField postCodePanel;
  private final ErrorableTextField boxPanel;
  private final ErrorableTextField cityPanel;
  private final ErrorableTextField streetPanel;

  /**
   * Creates a gridbag constraint for a vertical grid.
   * @param fieldNum The row num in the grid.
   * @return The gridbag constraint.
   */
  public static GridBagConstraints makeGbc(int fieldNum) {
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.gridy = fieldNum;
    gbc.ipadx = 400;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    return gbc;
  }

  /**
   * Creates a new view for the company gui.
   */
  protected NewCompanyView(NewCompanyModel model, JTextField companyNameField,
                           JTextField numerField, JTextField postCodeField, JTextField streetField,
                           JTextField cityField, JTextField boxField) {
    super(new GridBagLayout());

    this.model = model;

    this.companyNamePanel = new ErrorableTextField(companyNameField, "Nom", 16);
    this.add(this.companyNamePanel, makeGbc(0));

    this.add(new JLabelFont("Adresse", 20), makeGbc(1));

    this.streetPanel = new ErrorableTextField(streetField, "Rue", 16);
    this.add(this.streetPanel, makeGbc(2));

    this.cityPanel = new ErrorableTextField(cityField, "Ville", 16);
    this.add(this.cityPanel, makeGbc(3));

    this.boxPanel = new ErrorableTextField(boxField, "Boite", 16);
    this.add(this.boxPanel, makeGbc(4));

    this.numPanel = new ErrorableTextField(numerField, "Numéro", 16);
    this.add(this.numPanel, makeGbc(5));

    this.postCodePanel = new ErrorableTextField(postCodeField, "Code postal", 16);
    this.add(this.postCodePanel, makeGbc(6));

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    companyNamePanel.setError(model.getCompanyNameError());
    streetPanel.setError(model.getStreetError());
    cityPanel.setError(model.getCityError());
    boxPanel.setError(model.getBoxError());
    numPanel.setError(model.getNumError());
    postCodePanel.setError(model.getPostCodeError());
  }
}
