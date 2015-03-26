package paoo.cappuccino.ihm.newBusinessDay;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * View for the new BusinessDay Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewBusinessDayView extends JPanel implements ChangeListener {

  private NewBusinessDayModel model;



  /**
   * @param model
   * @param spinnerDate
   */
  public NewBusinessDayView(NewBusinessDayModel model, JSpinner spinnerDate) {
    super(new GridBagLayout());
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;
    JPanel spinnerPanel = new JPanel(new GridLayout(1, 2));
    GridBagConstraints gbc = new GridBagConstraints();
    spinnerPanel.add(new JLabelFont("Date de la nouvelle journée", 16));
    spinnerPanel.add(spinnerDate);// TODO centrer verticalement
    this.add(spinnerPanel, gbc);


    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {

  }
}
