package paoo.cappuccino.ihm.contactsearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

@SuppressWarnings("serial")
public class ContactSearchViewController extends JPanel {

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

    ContactSearchView view = new ContactSearchView(model, contactUcc, companyUcc);
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
  }
}
