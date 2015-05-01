package paoo.cappuccino.ihm.searchparticipations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.StateCellRenderer;

public class ParticipationSearchView extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 1189296753762441036L;
  private final DefaultTableModel tableModel;
  private final JScrollPane scrollPane;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  /**
   * Creates a view for the participation search screen.
   * 
   * @param model The model of the view.
   * @param companyUcc The app instance of the company ucc.
   * @param businessDayUcc The app instance of the business day ucc.
   */
  public ParticipationSearchView() {


    setLayout(new BorderLayout());


    String[] tableTitles =
        new String[] {"Nom entreprise", "Adresse entreprise", "Date d'enregistrement", "État"};
    this.tableModel = new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.table = new JTable(tableModel);

    TableColumn companyCol = table.getColumn(tableTitles[0]);
    companyCol.setCellRenderer(new CompanyCellRenderer());

    TableColumn dateCol = table.getColumn(tableTitles[2]);
    dateCol.setCellRenderer(new DateCellRenderer());
    dateCol.setMaxWidth(dateCol.getMaxWidth() / 4);

    TableColumn stateCol = table.getColumn(tableTitles[3]);
    stateCol.setCellRenderer(new StateCellRenderer());
    stateCol.setMaxWidth(stateCol.getMaxWidth() / 4);

    this.scrollPane = new JScrollPane(table);
    scrollPane.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP, IhmConstants.M_GAP));
    this.add(scrollPane);
  }

  @Override
  public void stateChanged(ChangeEvent event) {


    if (table.getRowCount() > 0) {
      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);
        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }


    } else {
      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPadding.add(new JLabel(IhmConstants.ERROR_NO_PARTICIPATIONS));
        this.remove(this.scrollPane);
        this.add(centerPadding);
        this.removedWidget = true;
        this.revalidate();
        this.repaint();
      }
    }
  }

  JTable getTable() {
    return this.table;
  }


}
