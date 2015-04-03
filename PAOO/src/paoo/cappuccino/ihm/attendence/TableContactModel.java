package paoo.cappuccino.ihm.attendence;

import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * @author Opsomer Mathias
 */
public class TableContactModel extends AbstractTableModel {

  private String[] titles;
  private Object[][] data;


  /**
   */
  public TableContactModel() {
    this.titles =
        new String[] {"Nom contact", "Prénom contact", "Email contact",
            "Sélèctionner"};
    this.data = new Object[0][0];
  }

  public void changeData(IContactDto[] listContact) {
    if (listContact == null) {
      return;
    }
    data = new Object[titles.length][listContact.length];
    for (int i = 0; i < listContact.length; i++) {
      data[i][0] = listContact[i].getFirstName();
      data[i][1] = listContact[i].getLastName();
      data[i][2] = listContact[i].getEmail();
      data[i][3] = Boolean.FALSE;
    }
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

  @Override
  public void setValueAt(Object aValue, int row, int column) {
    data[row][column] = aValue;
  }
}
