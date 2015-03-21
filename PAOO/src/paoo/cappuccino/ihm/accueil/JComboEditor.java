/**
 * 
 */
package paoo.cappuccino.ihm.accueil;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

  protected JComboBox<State> combo;
  private ComboListener cListener = new ComboListener();


  /**
   * @param comboBox
   */
  public JComboEditor(JComboBox<State> comboBox) {
    super(new JComboBox<State>());
    combo = new JComboBox<State>();
    combo.setOpaque(true);
    combo.addActionListener(cListener);
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    cListener.setRow(row);
    cListener.setColumn(column);
    cListener.setTable(table);
    if (this.combo.getItemCount() == 0) {
      combo.addItem(State.CANCELLED);
      combo.addItem(State.INVITED);
      // TODO build celon ce qu'il se trouve dans la cellule
    }
    return combo;
  }

  class ComboListener implements ActionListener {
    private int column, row;
    private JTable table;

    public void setColumn(int col) {
      this.column = col;
    }

    public void setRow(int row) {
      this.row = row;
    }

    public void setTable(JTable table) {
      this.table = table;
    }

    public void actionPerformed(ActionEvent event) {
      System.out.println("changement etat " + table.getValueAt(row, 0));
      // TODO
    }
  }
}
