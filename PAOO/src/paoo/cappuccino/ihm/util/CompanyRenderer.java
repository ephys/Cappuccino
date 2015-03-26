package paoo.cappuccino.ihm.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import paoo.cappuccino.business.dto.ICompanyDto;


public class CompanyRenderer implements ListCellRenderer<ICompanyDto> {

  @Override
  public Component getListCellRendererComponent(JList<? extends ICompanyDto> list,
      ICompanyDto value, int index, boolean isSelected, boolean cellHasFocus) {
    if (value == null)
      return new JLabel();
    return new JLabel(value.getName());
  }
}
