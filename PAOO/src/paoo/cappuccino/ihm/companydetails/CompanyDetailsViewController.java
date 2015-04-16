package paoo.cappuccino.ihm.companydetails;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.ucc.IUserUcc;

@SuppressWarnings("serial")
public class CompanyDetailsViewController extends JPanel implements ChangeListener {
  private final JTable contactsTable;
  private final CompanyDetailsModel model;
  private final IContactUcc contactUcc;
  private final CompanyDetailsView view;

  public CompanyDetailsViewController(CompanyDetailsModel model, MenuModel menu,
                                      IContactUcc contactUcc, IUserUcc userUcc) {
    this.model = model;
    this.contactUcc = contactUcc;

    this.view = new CompanyDetailsView(model, userUcc);
    this.add(view);

    contactsTable = view.getContactsTable();
    contactsTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          menu.setCurrentPage(MenuEntry.CONTACT_DETAILS,
                              contactsTable.getModel().getValueAt(contactsTable.getSelectedRow(),
                                                                  0));
        }
      }
    });

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    ICompanyDto company = model.getCompanyDto();
    if (company == null) {
      view.stateChanged(null);
      return;
    }

    List<IContactDto> contacts = contactUcc.getContactByCompany(company.getId());
    view.stateChanged(contacts);

    DefaultTableModel tableModel = (DefaultTableModel) contactsTable.getModel();
    tableModel.setRowCount(contacts.size());
    for (int i = 0; i < contacts.size(); i++) {
      IContactDto contact = contacts.get(i);

      tableModel.setValueAt(contact, i, 0);
      tableModel.setValueAt(contact.getFirstName(), i, 1);
      tableModel.setValueAt(contact.getEmail(), i, 2);
      tableModel.setValueAt(contact.getPhone(), i, 3);
    }
  }
}
