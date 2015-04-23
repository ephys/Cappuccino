package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Renders a {@link IContactDto contact} in a JTable.
 */
@SuppressWarnings("serial")
public class ContactCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean hasFocus, int row,
      int column) {
    super.getTableCellRendererComponent(table, value, isSelected,
        hasFocus, row, column);
    if (value instanceof IContactDto) {
      setText(((IContactDto) value).getLastName());
    }

    return this;
  }
}
