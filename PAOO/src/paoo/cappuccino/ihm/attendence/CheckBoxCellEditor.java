package paoo.cappuccino.ihm.attendence;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * @author Opsomer Mathias
 *
 *
 */
public class CheckBoxCellEditor extends DefaultCellEditor {


  public CheckBoxCellEditor(JCheckBox box, AttendanceModel model,
      JTable table) {
    super(box = new JCheckBox());
    box.addActionListener(e -> {
      int nbChecked = 0;
      for (int i = 0; i < table.getRowCount(); i++) {
        if ((boolean) table.getModel().getValueAt(
            i, table.getColumnCount() - 1)) {
          nbChecked++;
        }
      }

      if (((JCheckBox) e.getSource()).isSelected()) {// car s excecute avant l'action check
        nbChecked++;
      } else {
        nbChecked--;
      }

      if (nbChecked == table.getRowCount()) {
        model.setAllSelected(true);
      } else if (nbChecked == 0) {
        model.setAllSelected(false);
      }
    });
  }
}
