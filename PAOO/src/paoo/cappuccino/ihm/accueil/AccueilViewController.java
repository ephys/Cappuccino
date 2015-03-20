package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IBusinessDayUcc;


/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;
  private TableModel dataModel;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu, IGuiManager guiManager,
                               IBusinessDayUcc dayUcc) {
    super(new BorderLayout());

    this.model = model;
    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));// TODO listener
    JComboBox<IBusinessDayDto> dayList = new JComboBox<>(dayUcc.getBusinessDays());
    dayList.addActionListener(e -> {
      int index = dayList.getSelectedIndex();
      if (index == -1) {
        model.setSelectedDay(null);
      }
    });

    daylistPanel.add(new JLabelFont("journée du : "));
    // daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    String[] companies = new String[] {"entreprise 1", "entreprise 2", "entreprise 3"};
    String[] titles = new String[] {"Nom entreprise", "État"};
    JTable table = new JTable(new TableCompaniesModel(companies, titles));
    table.setDefaultRenderer(JComboButtonCell.class, new JComboButtonCell());
    table.setDefaultEditor(JComboButtonCell.class, new JComboButtonCell());
    table.setRowHeight(35);
    this.add(new JScrollPane(table));
  }

  @Override
  public void stateChanged(ChangeEvent event) {}
}
