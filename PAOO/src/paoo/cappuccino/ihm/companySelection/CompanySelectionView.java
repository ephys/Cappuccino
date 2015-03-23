package paoo.cappuccino.ihm.companySelection;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

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

    if (model.getCompanyDto() != null && model.getResetTable()) {

      this.table.setModel(resetTableModel(model.getCompanyDto()));
      model.setResetTable(false);
      return;
    } else if (model.getCompanyDto() == null) {

      this.table.setModel(new tableModel());
      // JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
      // centerPadding.add(new JLabel("Il n'y a aucune entreprise disponible."));

      // this.add(centerPadding);
      // return;
    }

    boolean selectAll = model.getSelectAll();

    for (int i = 0; i < this.table.getRowCount(); i++) {

      this.table.setValueAt(selectAll, i, this.table.getColumnCount() - 1);
    }


  }

  public AbstractTableModel resetTableModel(ICompanyDto[] companyDto) {

    return new tableModel(companyDto);

  }

  public JTable getTable() {

    return this.table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement",
        "Selectionner"};
    Object[][] data;
    Object[][] data1 = { {"E001", "DSQD", 1 / 10 / 20, false},
        {"E002", "DQSD", 1 / 10 / 20, false}, {"E003", "CDSQDQ", 1 / 10 / 20, false},
        {"E004", "DSQDQ", 1 / 10 / 20, false}};

    // a enlever
    public tableModel() {

    }

    public tableModel(ICompanyDto[] companyDto) {

      this.data = new Object[companyDto.length][];

      for (int i = 0; i < companyDto.length; i++) {

        this.data[i] =
            new Object[] {companyDto[i].getName(), companyDto[i].getAddressTown(),
                companyDto[i].getRegisterDate().toString(), false};
      }

    }

    // changer data1 par data
    public int getRowCount() {
      return data1.length;
    }

    public int getColumnCount() {
      return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
      return data1[rowIndex][columnIndex];
    }

    public String getColumnName(int column) {
      return columns[column];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {

      if (columnIndex == data1[0].length - 1)
        return true;
      return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

      data1[rowIndex][columnIndex] = aValue;
    }

    public Class<?> getColumnClass(int columnIndex) {
      return data1[0][columnIndex].getClass();
    }
  }
}
