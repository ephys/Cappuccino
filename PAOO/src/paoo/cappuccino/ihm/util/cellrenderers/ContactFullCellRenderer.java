package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Renders a {@link IContactDto date} in a JTable.
 */
public class ContactFullCellRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean hasFocus, int row,
      int column) {
    super.getTableCellRendererComponent(table, value, isSelected,
        hasFocus, row, column);
    if (value instanceof IContactDto) {
      IContactDto contact = (IContactDto) value;
      this.setText(contact.getLastName() + " " + contact.getFirstName());
      if (table.getValueAt(row, column - 1) instanceof Boolean)
        if ((boolean) table.getValueAt(row, column - 1)) {
          this.setForeground(Color.GRAY);
        }
    } else {
      setText(null);
    }

    return this;
  }
}
