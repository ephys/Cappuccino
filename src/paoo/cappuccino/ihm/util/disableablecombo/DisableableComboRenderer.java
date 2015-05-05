package paoo.cappuccino.ihm.util.disableablecombo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class DisableableComboRenderer extends BasicComboBoxRenderer {

  private static final long serialVersionUID = -984932432414L;

  private final ListSelectionModel enabledItems;
  private Color disabledColor = Color.lightGray;

  public DisableableComboRenderer(ListSelectionModel enabled) {
    this.enabledItems = enabled;
  }

  public void setDisabledColor(Color disabledColor) {
    this.disabledColor = disabledColor;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index,
                                                boolean isSelected, boolean cellHasFocus) {
    Component cell = super.getListCellRendererComponent(list, value, index,
                                                        isSelected, cellHasFocus);

    if (!enabledItems.isSelectedIndex(index)) {
      cell.setForeground(disabledColor);
    }

    return cell;
  }
}