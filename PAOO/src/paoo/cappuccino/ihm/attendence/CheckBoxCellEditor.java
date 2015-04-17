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


  public CheckBoxCellEditor(JCheckBox box, AttendanceModel model, JTable table, JCheckBox all) {
    super(box = new JCheckBox());
    box.addActionListener(e -> {
      int nbChecked = 0;
      for (int i = 0; i < table.getRowCount(); i++) {
        if ((boolean) table.getModel().getValueAt(i, table.getColumnCount() - 1)) {
          nbChecked++;
        }
      }

      if (((JCheckBox) e.getSource()).isSelected()) {// car s excecute avant l'action check
        nbChecked++;
      } else {
        nbChecked--;
      }

      if (model.isSelectAll() && nbChecked == (table.getRowCount() - 1)) {

        model.setNotDeselectAll(true);
        all.setSelected(false);
        return;
      }
      if (!model.isSelectAll() && nbChecked == table.getRowCount()) {

        model.setNotDeselectAll(true);
        all.setSelected(true);
        return;
      }
    });
  }
}
