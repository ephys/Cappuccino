package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements
    ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model) {
    super(new BorderLayout());
    // this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP,
    // IhmConstants.M_GAP, 0, IhmConstants.M_GAP));
    this.setLayout(new BorderLayout());
    model.addChangeListener(this);

    // north
    JPanel selectDay = new JPanel(new FlowLayout(FlowLayout.CENTER));
    selectDay.add(new JLabel("journée du : "));
    JComboBox<IBusinessDayDto> day =
        new JComboBox<IBusinessDayDto>(model.getAllDays());// TODO stateChanged if day change
    selectDay.add(day);
    this.add(selectDay, BorderLayout.NORTH);

    // center
    String[] columnNames = {"Nom entreprise", "État"};
    ICompanyDto[] companies =
        model.getCompanies(day.getItemAt(day.getSelectedIndex()));

    for (ICompanyDto c : companies) {
      JPanel stateControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
      JButton cancel = new JButton("Annuler");
      cancel.addActionListener(e -> {
        model.removeCompany(c);
      });
      JComboBox<IParticipationDto.State> state =
          new JComboBox<IParticipationDto.State>(model.getPossibleState(c));
      stateControl.add(state);
      stateControl.add(cancel);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    // TODO gérer la supression d'une entreprise ou le changement de journée

  }
}
