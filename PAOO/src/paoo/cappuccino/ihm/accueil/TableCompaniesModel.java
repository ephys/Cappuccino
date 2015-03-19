package paoo.cappuccino.ihm.accueil;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableCompaniesModel extends AbstractTableModel implements TableModel {
  String[] companies;
  String[] titles;

  public TableCompaniesModel(String[] companies, String[] titles) {
    this.companies = companies;
    this.titles = titles;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
      case 1:
        return JComboButtonCell.class;
      default:
        return String.class;
    }
  }

  @Override
  public int getColumnCount() {
    return titles.length;
  }

  @Override
  public String getColumnName(int columnIndex) {
    return titles[columnIndex];
  }

  @Override
  public int getRowCount() {
    return (companies == null) ? 0 : companies.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return (companies == null) ? null : companies[rowIndex];
  }

  @Override
  public boolean isCellEditable(int arg0, int arg1) {
    return false;
  }
}
