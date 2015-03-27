package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * View for the company selection Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class CompanySelectionView extends JPanel implements ChangeListener {

  private CompanySelectionModel model;
  private JTable table;

  public CompanySelectionView(CompanySelectionModel model) {

    setLayout(new BorderLayout());
    this.model = model;
    this.table = new JTable();
    this.add(new JScrollPane(table));
    this.model.addChangeListener(this);
  }


  @Override
  public void stateChanged(ChangeEvent event) {


    if (model.getCompanyDto() != null && this.table.getRowCount() == 0) {

      this.table.setModel(new tableModel(model.getCompanyDto()));

      this.table.getColumnModel().getColumn(this.table.getColumnCount() - 1).setPreferredWidth(1);
      return;

    } else if (model.getCompanyDto() == null) {

      JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
      centerPadding.add(new JLabel(
          "Ce message vous est affiché : soit parce qu'il n'y a plus d'entreprise disponible, soit parce "
              + "qu'il n'y a plus de journée d'enteprise."));

      this.add(centerPadding);
      return;
    }

    boolean selectAll = model.getSelectAll();

    for (int i = 0; i < this.table.getRowCount(); i++) {

      this.table.setValueAt(selectAll, i, this.table.getColumnCount() - 1);
      this.table.repaint();
    }


  }


  public JTable getTable() {

    return this.table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement",
        "Selectionner"};
    Object[][] data;

    public tableModel(ICompanyDto[] companyDto) {

      this.data = new Object[companyDto.length][];

      for (int i = 0; i < companyDto.length; i++) {

        this.data[i] =
            new Object[] {companyDto[i].getName(), companyDto[i].getAddressTown(),
                companyDto[i].getRegisterDate().toString(), false};
      }

    }

    public int getRowCount() {
      return data.length;
    }

    public int getColumnCount() {
      return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
      return data[rowIndex][columnIndex];
    }

    public String getColumnName(int column) {
      return columns[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {

      if (columnIndex == data[0].length - 1)
        return true;
      return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

      data[rowIndex][columnIndex] = aValue;
    }

    public Class<?> getColumnClass(int columnIndex) {
      return data[0][columnIndex].getClass();
    }
  }
}
