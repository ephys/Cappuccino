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
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu, IGuiManager guiManager) {
    super(new BorderLayout());
    this.model = model;
    model.addChangeListener(this);

    // this.setBorder(BorderFactory.createEmptyBorder(IhmConstants.L_GAP,
    // IhmConstants.M_GAP, 0, IhmConstants.M_GAP));
    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JComboBox<IBusinessDayDto> dayList = new JComboBox<>(model.getAllDays());
    dayList.addActionListener(e -> {
      int index = dayList.getSelectedIndex();
      if (index == -1) {
        model.setSelectedDay(null);
      }

      model.setSelectedDay(dayList.getItemAt(index));
    });

    daylistPanel.add(new JLabel("journée du : "));
    daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);
    // center
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent event) {
    String[] columnNames = {"Nom entreprise", "État"};

    ICompanyDto[] companies = model.getCompanies();

    for (ICompanyDto c : companies) {
      JPanel stateControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
      JButton cancel = new JButton("Annuler");
      cancel.addActionListener(e -> model.removeCompany(c));
      JComboBox<IParticipationDto.State> state = new JComboBox<>(model.getPossibleState(c));
      stateControl.add(state);
      stateControl.add(cancel);
    }
  }
}
