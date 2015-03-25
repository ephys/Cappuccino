package paoo.cappuccino.ihm.newBusinessDay;

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
    super(new GridLayout(1, 2));
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;
    this.add(new JLabelFont("Date de la nouvelle journée", 16));
    JPanel spinnerPanel = new JPanel();
    spinnerPanel.add(spinnerDate);// TODO centrer verticalement
    this.add(spinnerPanel);


    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {

  }
}
