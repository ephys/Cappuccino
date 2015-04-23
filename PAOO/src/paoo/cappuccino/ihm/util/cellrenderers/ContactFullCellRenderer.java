package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Renders a {@link java.time.LocalDateTime date} in a JTable.
 */
@SuppressWarnings("serial")
public class ContactFullCellRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean hasFocus, int row,
      int column) {
    super.getTableCellRendererComponent(table, value, isSelected,
        hasFocus, row, column);
    if (value instanceof IContactDto) {
      IContactDto contact = (IContactDto) value;
      setText(contact.getLastName() + " " + contact.getFirstName() + " "
          + contact.getEmail() + " " + contact.getPhone());
    }
    return this;
  }
}
