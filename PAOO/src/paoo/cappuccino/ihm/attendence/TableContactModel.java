package paoo.cappuccino.ihm.attendence;

import javax.swing.table.AbstractTableModel;

/**
 * @author Opsomer Mathias
 */
public class TableContactModel extends AbstractTableModel {

  private String[] titles;
  private Object[][] data;


  public TableContactModel(String[] titles, Object[][] data) {
    this.titles = titles;
    this.data = data;
  }


  public Class<?> getColumnClass(int col) {
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
    return col == titles.length - 1;
  }
}
