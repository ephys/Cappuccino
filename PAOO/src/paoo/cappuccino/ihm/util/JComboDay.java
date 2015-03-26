package paoo.cappuccino.ihm.util;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import paoo.cappuccino.business.dto.IBusinessDayDto;

public class JComboDay extends JPanel {
  private final JComboBox<IBusinessDayDto> combo;
  private JLabel label = null;

  public JComboDay(IBusinessDayDto[] businessDays) {
    super(new FlowLayout(FlowLayout.CENTER));
    label = new JLabel("Journée du");
    combo = new JComboBox<IBusinessDayDto>(businessDays);
    combo.setRenderer(new dayRenderer());
    combo.setSelectedIndex(0);
    this.add(label);
    this.add(combo);
  }

  public JComboBox<IBusinessDayDto> getCombo() {
    return combo;
  }

  class dayRenderer implements ListCellRenderer<IBusinessDayDto> {

    @Override
    public Component getListCellRendererComponent(JList<? extends IBusinessDayDto> list,
        IBusinessDayDto value, int index, boolean isSelected, boolean cellHasFocus) {
      if (value == null)
        return new JLabel();
      return new JLabel(value.getEventDate().toString());
    }

  }
}
