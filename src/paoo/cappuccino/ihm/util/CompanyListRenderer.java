package paoo.cappuccino.ihm.util;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * Renders a company in a JList.
 */
public class CompanyListRenderer extends DefaultListCellRenderer {

  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                boolean isSelected, boolean cellHasFocus) {
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

    setText(value == null ? null : ((ICompanyDto) value).getName());

    return this;
  }
}
