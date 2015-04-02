package paoo.cappuccino.ihm.util;

import java.awt.Component;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.entity.IBusinessDay;

public class JComboDay extends JPanel {
  private final JComboBox<IBusinessDayDto> combo;


  public JComboDay(IBusinessDayDto[] businessDays) {
    super(new FlowLayout(FlowLayout.CENTER));
    JLabel label = new JLabel("Journée du");
    combo = new JComboBox<IBusinessDayDto>(businessDays);
    combo.setRenderer(new DayRenderer());
    this.add(label);
    this.add(combo);
  }

  public JComboBox<IBusinessDayDto> getCombo() {
    return combo;
  }

  private static class DayRenderer extends BasicComboBoxRenderer {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

   
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);

      setText(value == null ? null : ((IBusinessDay) value).getEventDate().format(formatter));
      
      return this;
    }
  }
}
