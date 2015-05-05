package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.ihm.util.LocalizationUtil;

/**
 * Renders a {@link State state} in a JTable.
 */
public class StateCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean isFocus, int row, int col) {
    super.getTableCellRendererComponent(table, value, isSelected, isFocus,
        row, col);
    if (value instanceof State) {
      State state = (State) value;
      setText(LocalizationUtil.localizeState(state));
    } else {
      setText(null);
    }

    return this;
  }
}
