package paoo.cappuccino.ihm.accueil;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableCompaniesModel extends AbstractTableModel implements TableModel {
  Object[][] data;
  String[] titles;

  public TableCompaniesModel(String[] companies, String[] titles) {
    data = new Object[titles.length][companies.length];
    for (int i = 0; i < companies.length; i++) {
      data[i][0] = companies[i];
      data[i][1] = new JComboBox<String>(new String[] {"un", "deux", "trois"});
      data[i][2] = new JButton("Annuler");
    }
    this.titles = titles;
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
  public boolean isCellEditable(int arg0, int arg1) {
    return false;
  }
}
