package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;
  private TableModel dataModel = null;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu, IGuiManager guiManager) {
    super(new BorderLayout());
    this.model = model;
    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // JComboBox<IBusinessDayDto> dayList = new JComboBox<>(model.getAllDays());
    // dayList.addActionListener(e -> {
    // int index = dayList.getSelectedIndex();
    // if (index == -1) {
    // model.setSelectedDay(null);
    // }
    //
    // model.setSelectedDay(dayList.getItemAt(index));
    // });

    daylistPanel.add(new JLabelFont("journée du : "));
    // daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center

    JTable table = new JTable(dataModel);
    JScrollPane scrollpane = new JScrollPane(table);
    this.add(scrollpane);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent event) {

    dataModel = new AbstractTableModel() {
      private String[] titles = {"Nom entreprise, État"};
      private ICompanyDto[] companies = model.getCompanies();


      public int getColumnCount() {
        return titles.length;
      }

      public int getRowCount() {
        return companies.length;
      }

      public Object getValueAt(int row, int col) {
        return companies[0];
      }
    };


  }
}
