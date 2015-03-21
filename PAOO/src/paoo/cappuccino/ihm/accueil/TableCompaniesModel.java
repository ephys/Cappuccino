package paoo.cappuccino.ihm.accueil;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableCompaniesModel extends AbstractTableModel implements
    TableModel {
  String[] titles;
  Object[][] data;


  public TableCompaniesModel(Object[][] data, String[] titles) {
    this.titles = titles;
    this.data = data;
  }

  @Override
  public Class getColumnClass(int col) {
    return data[0][col].getClass();
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
    return (data == null) ? 0 : data.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return (data == null) ? null : data[rowIndex][columnIndex];
  }


  @Override
  public boolean isCellEditable(int row, int col) {
    return true;
  }
}
