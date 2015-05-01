package paoo.cappuccino.ihm.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;

public class JTableMouseCompany extends JTable {

  private static final long serialVersionUID = -804950371446416922L;

  public JTableMouseCompany(TableModel model, MenuModel menu) {
    super(model);
    setRowHeight(35);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company = (ICompanyDto) model.getValueAt(getSelectedRow(), 0);
          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });
  }
}
