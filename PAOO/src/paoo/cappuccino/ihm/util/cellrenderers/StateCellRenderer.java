package paoo.cappuccino.ihm.util.cellrenderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.util.LocalizationUtil;

/**
 * Renders a {@link paoo.cappuccino.business.dto.IParticipationDto.State state} in a JTable.
 */
public class StateCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean isFocus, int row, int col) {
    super.getTableCellRendererComponent(table, value, isSelected, isFocus,
        row, col);
    if (value instanceof IParticipationDto) {
      IParticipationDto.State state = (IParticipationDto.State) value;
      setText(LocalizationUtil.localizeState(state));
    }

    return this;
  }
}
