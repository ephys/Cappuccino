package paoo.cappuccino.ihm.util;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;

public class JComboDay extends JPanel {

  private static final long serialVersionUID = -1473411391147068211L;
  private final JComboBox<IBusinessDayDto> combo;

  /**
   * Creates a JComboBox rendering a list of business days.
   *
   * @param businessDays The list of business days to render in the combo box.
   */
  public JComboDay(IBusinessDayDto[] businessDays, MenuModel menu) {
    super(new FlowLayout(FlowLayout.CENTER));
    combo = new JComboBox<>(businessDays);
    combo.setRenderer(new DayRenderer());

    if (businessDays.length == 0) {
      this.add(new JLabel(IhmConstants.ERROR_NO_BUSINESS_DAY));
      JButton newDay = new JButton("Créer journée");
      newDay.addActionListener(e -> menu.setCurrentPage(MenuEntry.CREATE_BDAY));
      this.add(newDay);
    } else {
      this.add(new JLabel("Journée du"));
      this.add(combo);
    }
  }

  public JComboBox<IBusinessDayDto> getCombo() {
    return combo;
  }

  private static class DayRenderer extends BasicComboBoxRenderer {

    private static final long serialVersionUID = -2663847848596827426L;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);

      setText(value == null ? null : LocalizationUtil.localizeDate(((IBusinessDay) value)
          .getEventDate()));

      return this;
    }
  }
}
