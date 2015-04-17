package paoo.cappuccino.ihm.attendence;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * @author Opsomer Mathias
 */
public class TableContactModel extends AbstractTableModel {

  private String[] titles;
  private Object[][] data;

  public TableContactModel() {
    this.titles = new String[] {"Nom contact", "Prénom contact", "Email contact", "Sélèctionner"};
    this.data = new Object[0][3];
  }

  public void changeData(List<IContactDto> listContact) {
    if (listContact == null || listContact.size() == 0) {
      return;
    }
    data = new Object[titles.length][listContact.size()];
    for (int i = 0; i < listContact.size(); i++) {
      data[i][0] = listContact.get(i).getFirstName();
      data[i][1] = listContact.get(i).getLastName();
      data[i][2] = listContact.get(i).getEmail();
      data[i][3] = Boolean.FALSE;
    }
  }

  public Class<?> getColumnClass(int col) {
    return (data == null) ? null : data[0][col].getClass();
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

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    data[row][column] = aValue;
  }
}
