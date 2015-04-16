package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Component;
import java.time.LocalDateTime;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.ihm.util.LocalizationUtil;

/**
 * Renders a {@link java.time.LocalDateTime date} in a JTable.
 */
public class DateCellRenderer extends DefaultTableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                 boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    setText(LocalizationUtil.localizeDate((LocalDateTime) value));

    return this;
  }
}
