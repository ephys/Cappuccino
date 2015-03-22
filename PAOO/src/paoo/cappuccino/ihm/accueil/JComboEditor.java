package paoo.cappuccino.ihm.accueil;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.IParticipationDto.State;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class JComboEditor extends DefaultCellEditor {

  /**
   * @param comboBox
   */
  public JComboEditor(JComboBox<State> comboBox) {
    super(comboBox);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    return (JComboBox<State>) value;
  }
}
