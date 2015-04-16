package paoo.cappuccino.ihm.companydetails;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ucc.IUserUcc;

public class CompanyDetailsView extends JPanel {

  private final JTable contactsTable;
  private final JPanel contactList = new JPanel(new BorderLayout());
  private final CompanyDetailsModel model;
  private final IUserUcc userUcc;
  private final JPanel companyPanel = new JPanel(new BorderLayout());

  public CompanyDetailsView(CompanyDetailsModel model, IUserUcc userUcc) {
    super(new BorderLayout());
    this.model = model;
    this.userUcc = userUcc;

    String[] tableTitles = new String[]{"Nom", "prenom", "Mail", "Téléphone"};
    this.contactsTable = new JTable(new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    contactsTable.setRowHeight(35);
    contactsTable.getColumn(tableTitles[0]).setCellRenderer(new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(((IContactDto) value).getLastName());

        return this;
      }
    });

    JPanel contactWrapper = new JPanel(new BorderLayout());
    contactWrapper.add(new JLabelFont("Contacts", 17), BorderLayout.NORTH);
    contactWrapper.add(contactList, BorderLayout.CENTER);
    contactWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

    this.add(contactWrapper);
  }

  private void displayCompanyData() {
    final ICompanyDto company = model.getCompanyDto();
    if (company == null) {
      return;
    }

    final JLabel title = new JLabelFont(company.getName(), 20);
    title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    companyPanel.add(title, BorderLayout.NORTH);

    final JPanel center = new JPanel(new BorderLayout());

    final IUserDto companyCreator = userUcc.getUserById(company.getCreator());
    final String registerText = "Enregistré par " + (companyCreator == null ? "<Supprimé>" :
                                                     companyCreator.getFirstName()
                                                     + " " + companyCreator.getLastName())
                                + " le " + LocalizationUtil.localizeDate(model.getCompanyDto()
                                                                             .getRegisterDate());
    center.add(new JLabel(registerText), BorderLayout.NORTH);

    final JPanel addressPanel = new JPanel(new BorderLayout());
    addressPanel.add(new JLabelFont("Adresse", 17), BorderLayout.NORTH);

    final JPanel addressDataPanel = new JPanel(new GridLayout(3, 2));
    addressPanel.add(addressDataPanel);

    addressDataPanel.add(new JLabel("Rue : " + company.getAddressStreet()));
    addressDataPanel.add(new JLabel("Ville : " + company.getAddressTown()));
    addressDataPanel.add(new JLabel("Boite : " + company.getAddressMailbox()));
    addressDataPanel.add(new JLabel(
        "Numero : " + (company.getAddressNum() == null ? "N/A" : company.getAddressNum())));
    addressDataPanel.add(new JLabel("Code postal : " + company.getAddressPostcode()));

    companyPanel.add(addressPanel);

    this.add(companyPanel, BorderLayout.NORTH);
  }

  JTable getContactsTable() {
    return contactsTable;
  }

  public void stateChanged(List<IContactDto> contacts) {
    companyPanel.removeAll();
    displayCompanyData();

    contactList.removeAll();
    if (contacts == null) {
      return;
    }

    if (contacts.size() == 0) {
      JLabel errorLabel = new JLabel("Il n'y a aucune personne de contact pour cet entreprise.");
      errorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
      contactList.add(errorLabel);
    } else {
      contactList.add(new JScrollPane(contactsTable));
    }
  }
}
