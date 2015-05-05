package paoo.cappuccino.ihm.searchcontacts;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

public class ContactSearchViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = -3467457383300460357L;
  private final IContactUcc contactUcc;
  private final ICompanyUcc companyUcc;
  private final ContactSearchModel model;
  private ContactSearchView view;

  /**
   * Creates a view controller for the contact search view.
   *
   * @param model The model of the view.
   * @param menu The model of the menu.
   * @param companyUcc The app instance of the company ucc.
   */
  public ContactSearchViewController(ContactSearchModel model, MenuModel menu,
      ICompanyUcc companyUcc, IContactUcc contactUcc) {

    super(new BorderLayout());
    this.contactUcc = contactUcc;
    this.companyUcc = companyUcc;
    this.model = model;
    // first name
    JPanel panelFirstName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelFirstName.add(new JLabel("Prénom"));
    JTextField firstNameField = new JTextField(15);
    firstNameField.setText(model.getFirstName());
    firstNameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setFirstName(firstNameField.getText());
      }
    });
    panelFirstName.add(firstNameField);

    // last name
    JPanel panelLastName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panelLastName.add(new JLabel("Nom"));
    JTextField lastNameField = new JTextField(15);
    lastNameField.setText(model.getLastName());
    lastNameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent event) {
        model.setLastName(lastNameField.getText());
      }
    });
    panelLastName.add(lastNameField);

    JPanel searchingPanel = new JPanel(new GridLayout(1, 0));
    searchingPanel.add(panelLastName);
    searchingPanel.add(panelFirstName);

    this.view = new ContactSearchView();
    JTable table = view.getTable();
    table.setRowHeight(35);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          IContactDto contact =
              (IContactDto) table.getModel().getValueAt(table.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.CONTACT_DETAILS, contact);
        }
      }
    });

    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(view);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent arg0) {

    List<IContactDto> contacts =
        contactUcc.searchContact(model.getFirstName(), model.getLastName());
    buildTable(contacts);
  }

  private void buildTable(List<IContactDto> contacts) {
    DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
    tableModel.setRowCount(contacts.size());

    for (int i = 0; i < contacts.size(); i++) {
      IContactDto contact = contacts.get(i);
      ICompanyDto company = companyUcc.getCompanyById(contact.getCompany());

      tableModel.setValueAt(contact, i, 0);
      tableModel.setValueAt(contact.getFirstName(), i, 1);
      tableModel.setValueAt(company, i, 2);
      tableModel.setValueAt(contact.isEmailValid() ? contact.getEmail() : "invalide", i, 3);
      tableModel.setValueAt(contact.getPhone(), i, 4);
    }
    view.stateChanged(null);
  }
}
