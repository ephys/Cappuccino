package paoo.cappuccino.ihm.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * Renders a company in a JList.
 */
public class CompanyRenderer implements ListCellRenderer<ICompanyDto> {
  private final JLabel label = new JLabel();

  @Override
  public Component getListCellRendererComponent(JList<? extends ICompanyDto> list,
      ICompanyDto value, int index, boolean isSelected, boolean cellHasFocus) {

    label.setText(value == null ? null : value.getName());

    return label;
  }
}
