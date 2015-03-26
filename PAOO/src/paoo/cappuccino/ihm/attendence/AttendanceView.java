package paoo.cappuccino.ihm.attendence;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.ihm.util.IhmConstants;

/**
 * View for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class AttendanceView extends JPanel implements ChangeListener {

  private AttendanceModel model;



  public AttendanceView(AttendanceModel model) {
    super();
    this.setBorder(new EmptyBorder(0, IhmConstants.XL_GAP, 0, IhmConstants.XL_GAP));

    this.model = model;


    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {


  }
}
