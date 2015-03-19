package paoo.cappuccino.ihm.accueil;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class JComboButtonCell extends AbstractCellEditor implements TableCellEditor,
    TableCellRenderer {

  JPanel panel;

  JComboBox<String> combo;
  JButton cancel;
  String company;

  public JComboButtonCell() {
    combo = new JComboBox<String>(new String[] {"un", "deux", "trois"});
    combo.addActionListener(e -> {
      JOptionPane.showMessageDialog(null,
          "updating : " + company + " to : " + (String) combo.getSelectedItem());
    });
    cancel = new JButton("Annuler");
    cancel.addActionListener(e -> {
      JOptionPane.showMessageDialog(null, "supressing : " + company);
    });
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(combo);
    panel.add(cancel);
  }

  private void updateData(String company) {
    this.company = company;
  }



  @Override
  public Object getCellEditorValue() {
    return panel;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    String company = (String) value;
    updateData(company);
    return panel;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
      int row, int column) {
    String company = (String) value;
    updateData(company);
    return panel;
  }

}
