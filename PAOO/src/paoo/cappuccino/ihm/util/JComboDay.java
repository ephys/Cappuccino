package paoo.cappuccino.ihm.util;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import paoo.cappuccino.business.dto.IBusinessDayDto;

public class JComboDay extends JComboBox<IBusinessDayDto> {

  public JComboDay(IBusinessDayDto[] businessDays) {
    super(businessDays);
    this.setRenderer(new dayRenderer());
    this.setSelectedIndex(0);// TODO debug
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
