package paoo.cappuccino.ihm.searchcontacts;

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
import paoo.cappuccino.ihm.util.cellrenderers.ContactCellRenderer;

public class ContactSearchView extends JPanel implements ChangeListener {

  private static final long serialVersionUID = -9070612770896247771L;
  private final JScrollPane scrollPane;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  /**
   * Creates a new View for the contact search screen.
   */
  public ContactSearchView() {


    setLayout(new BorderLayout());

    String[] tableTitles = new String[] {"Nom", "Prénom", "Entreprise", "Mail", "Téléphone"};
    DefaultTableModel tableModel = new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.table = new JTable(tableModel);

    TableColumn contactCol = table.getColumn(tableTitles[0]);
    contactCol.setCellRenderer(new ContactCellRenderer());

    TableColumn companyCol = table.getColumn(tableTitles[2]);
    companyCol.setCellRenderer(new CompanyCellRenderer());

    this.scrollPane = new JScrollPane(table);
    scrollPane.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP, IhmConstants.M_GAP));
    this.add(scrollPane);
  }


  JTable getTable() {
    return this.table;
  }


  @Override
  public void stateChanged(ChangeEvent event) {


    if (table.getRowCount() != 0) {
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
        centerPadding.add(new JLabel(IhmConstants.ERROR_NO_CONTACTS));

        this.remove(this.scrollPane);
        this.add(centerPadding);

        this.removedWidget = true;
        this.revalidate();
        this.repaint();
      }
    }

  }
}
