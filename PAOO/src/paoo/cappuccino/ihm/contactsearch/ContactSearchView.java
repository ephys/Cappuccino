package paoo.cappuccino.ihm.contactsearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.ContactCellRenderer;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

@SuppressWarnings("serial")
public class ContactSearchView extends JPanel implements ChangeListener {

  private final ContactSearchModel model;
  private final IContactUcc contactUcc;
  private final ICompanyUcc companyUcc;
  private final DefaultTableModel tableModel;
  private final JScrollPane scrollPane;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  public ContactSearchView(ContactSearchModel model, IContactUcc contactUcc, ICompanyUcc companyUcc) {

    setLayout(new BorderLayout());
    this.model = model;
    this.companyUcc = companyUcc;
    this.contactUcc = contactUcc;


    String[] tableTitles = new String[] {"Nom", "Prénom", "Entreprise", "Mail", "Téléphone"};
    this.tableModel = new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.table = new JTable(tableModel);

    TableColumn contactCol = table.getColumn(tableTitles[0]);
    contactCol.setCellRenderer(new ContactCellRenderer());

    TableColumn companyCol = table.getColumn(tableTitles[2]);
    companyCol.setCellRenderer(new CompanyCellRenderer());


    this.scrollPane = new JScrollPane(table);
    this.add(scrollPane);
    this.model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {

    List<IContactDto> contacts =
        contactUcc.searchContact(model.getFirstName(), model.getLastName());

    if (contacts != null && contacts.size() != 0) {
      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);
        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }

      buildTable(contacts);
    } else {
      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));

        centerPadding.add(new JLabel("Il n'y a aucun contact correspondant"));

        this.remove(this.scrollPane);
        this.add(centerPadding);
        this.removedWidget = true;

      }
    }
    this.revalidate();
    this.repaint();
  }

  JTable getTable() {
    return this.table;
  }

  private void buildTable(List<IContactDto> contacts) {
    tableModel.setRowCount(contacts.size());

    for (int i = 0; i < contacts.size(); i++) {
      IContactDto contact = contacts.get(i);
      ICompanyDto company = companyUcc.getCompanyById(contact.getCompany());

      tableModel.setValueAt(contact, i, 0);
      tableModel.setValueAt(contact.getLastName(), i, 1);
      tableModel.setValueAt(company, i, 2);
      tableModel.setValueAt(contact.getEmail(), i, 3);
      tableModel.setValueAt(contact.getPhone(), i, 4);
    }
  }


}
