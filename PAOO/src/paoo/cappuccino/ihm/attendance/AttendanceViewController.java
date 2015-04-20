package paoo.cappuccino.ihm.attendance;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.util.CompanyListRenderer;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ihm.util.cellrenderers.ContactCellRenderer;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

/**
 * ViewController for the attendance selection gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class AttendanceViewController extends JPanel implements ChangeListener {

  private boolean companyNeedsUpdate = true;
  private boolean contactNeedsUpdate = true;

  private final AttendanceModel model;
  private final IBusinessDayUcc businessDayUcc;
  private final IContactUcc contactUcc;
  private final ICompanyUcc companyUcc;

  private final JLabel companyComboMessage = new JLabel();
  private final JComboBox<ICompanyDto> comboCompanies =
      new JComboBox<>(new DefaultComboBoxModel<>());
  private final JComboBox<IBusinessDayDto> comboDay;

  private final JPanel contactPanel;
  private final JCheckBox selectAllCheckbox;
  private final JTable contactsTable;

  private final JButton submitButton;

  /**
   * Creates a new ViewController for the attendance selection gui.
   */
  public AttendanceViewController(AttendanceModel model, ICompanyUcc companyUcc,
                                  IBusinessDayUcc businessDayUcc, IContactUcc contactUcc) {
    super(new BorderLayout());
    this.model = model;
    this.businessDayUcc = businessDayUcc;
    this.contactUcc = contactUcc;
    this.companyUcc = companyUcc;

    this.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
                                   IhmConstants.M_GAP, IhmConstants.M_GAP));

    JPanel top = new JPanel(new GridLayout(1, 1));
    this.add(top, BorderLayout.NORTH);

    // Business day combobox
    List<IBusinessDayDto> listDay = businessDayUcc.getBusinessDays();
    JComboDay dayPanel = new JComboDay(listDay.toArray(new IBusinessDayDto[listDay.size()]));
    this.comboDay = dayPanel.getCombo();
    this.comboDay.addActionListener(event -> {
      if (model.getSelectedDay() != comboDay.getSelectedItem()) {
        companyNeedsUpdate = true;
        model.setSelectedDay((IBusinessDayDto) comboDay.getSelectedItem());
      }
    });

    if (listDay.isEmpty()) {
      JPanel noDay = new JPanel();
      noDay.add(new JLabel("Aucune journée des entreprises disponible."));
      top.add(noDay);
    } else {
      top.add(dayPanel);
    }

    // company combobox
    JPanel companyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    companyPanel.add(companyComboMessage);
    companyPanel.add(comboCompanies);

    comboCompanies.addActionListener(e -> {
      contactNeedsUpdate = true;
      model.setSelectedCompany((ICompanyDto) comboCompanies.getSelectedItem());
    });
    comboCompanies.setRenderer(new CompanyListRenderer());
    top.add(companyPanel);

    // Contact table
    this.contactPanel = new JPanel(new BorderLayout());
    this.add(contactPanel, BorderLayout.CENTER);
    this.selectAllCheckbox = new JCheckBox("Tout cocher");
    JPanel selectAllPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    selectAllPanel.add(selectAllCheckbox);
    contactPanel.add(selectAllPanel, BorderLayout.NORTH);

    String[] tableTitles = new String[]{"Nom contact", "Prénom contact",
                                        "Email contact", "Sélectionner"};
    this.contactsTable = new JTable(new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 3;
      }
    }) {
      @Override
      public Class getColumnClass(int column) {
        switch (column) {
          case 0:
            return IContactDto.class;
          case 1:
            return String.class;
          case 2:
            return String.class;
          case 3:
            return Boolean.class;
          default:
            throw new IllegalArgumentException(column + " > 3 | < 0");
        }
      }
    };

    contactsTable.setRowHeight(35);
    contactsTable.getColumn(tableTitles[0]).setCellRenderer(new ContactCellRenderer());
    TableColumn columnCheckBox = contactsTable.getColumn(tableTitles[3]);
    columnCheckBox.setMaxWidth(columnCheckBox.getMaxWidth() / 6);
    contactPanel.add(new JScrollPane(contactsTable));
    selectAllCheckbox.addActionListener(e -> {
      final boolean checked = selectAllCheckbox.isSelected();

      final DefaultTableModel tableModel = (DefaultTableModel) contactsTable.getModel();
      for (int i = 0; i < contactsTable.getRowCount(); i++) {
        tableModel.setValueAt(checked, i, 3);
      }
    });
    contactsTable.getModel().addTableModelListener(e -> {
      // check table initiated
      if (contactsTable.getRowCount() == 0 
          || contactsTable.getValueAt(contactsTable.getRowCount() - 1, 3) == null) {
        return;
      }

      if (e.getColumn() != 3) {
        return;
      }

      for (int i = 0; i < contactsTable.getRowCount(); i++) {
        if (!(boolean) contactsTable.getValueAt(i, 3)) {
          selectAllCheckbox.setSelected(false);
          return;
        }

        selectAllCheckbox.setSelected(true);
      }
    });

    JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    this.submitButton = new JButton("Valider");
    submitButton.addActionListener(e -> {
      List<IContactDto> contacts = new ArrayList<>();

      for (int i = 0; i < contactsTable.getRowCount(); i++) {
        if ((boolean) contactsTable.getValueAt(i, 3)) {
          contacts.add((IContactDto) contactsTable.getValueAt(i, 0));
        }
      }

      businessDayUcc.addInvitedContacts(contacts.toArray(new IContactDto[contacts.size()]),
                                        model.getSelectedDay());
    });
    controls.add(submitButton);

    this.add(controls, BorderLayout.SOUTH);

    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent arg0) {
    submitButton.setVisible(model.getSelectedCompany() != null);
    contactPanel.setVisible(model.getSelectedCompany() != null);

    final boolean dayChanged = model.getSelectedDay() == null
                               || !model.getSelectedDay().equals(comboDay.getSelectedItem());
    if (dayChanged) {
      comboDay.setSelectedItem(model.getSelectedDay());
    }

    if (model.getSelectedDay() == null) {
      companyComboMessage.setText("Sélectionnez une journée");
      comboCompanies.setVisible(false);

      return;
    }

    if (companyNeedsUpdate) {
      companyNeedsUpdate = false;
      updateCompanyCombo();
    }

    if (model.getSelectedCompany() == null
        || !model.getSelectedCompany().equals(comboCompanies
                                                  .getSelectedItem())) {
      comboCompanies.setSelectedItem(model.getSelectedCompany());
    }

    if (contactNeedsUpdate) {
      contactNeedsUpdate = false;
      buildContactsTable();
    }
  }

  private void updateCompanyCombo() {
    model.setSelectedCompany(null);

    List<ICompanyDto> companyList = companyUcc.getCompaniesByDay(model.getSelectedDay().getId());
    if (companyList.isEmpty()) {
      companyComboMessage.setText(IhmConstants.ERROR_NO_COMPANIES);
      comboCompanies.setVisible(false);

      return;
    }

    companyComboMessage.setText("Entreprise");
    DefaultComboBoxModel<ICompanyDto> companiesModel =
        (DefaultComboBoxModel<ICompanyDto>) comboCompanies.getModel();
    companiesModel.removeAllElements();
    companyList.forEach(companiesModel::addElement);

    comboCompanies.setVisible(true);
  }

  private void buildContactsTable() {
    selectAllCheckbox.setSelected(false);

    if (model.getSelectedCompany() == null) {
      return;
    }

    final List<IContactDto> participatingContacts = businessDayUcc.getInvitedContacts(
        model.getSelectedCompany(), model.getSelectedDay());
    final List<IContactDto> contacts = contactUcc.getContactByCompany(model.getSelectedCompany()
                                                                          .getId());

    DefaultTableModel tableModel = (DefaultTableModel) contactsTable.getModel();
    tableModel.setRowCount(contacts.size());


    for (int i = 0; i < contacts.size(); i++) {
      final IContactDto contact = contacts.get(i);

      tableModel.setValueAt(contact, i, 0);
      tableModel.setValueAt(contact.getFirstName(), i, 1);
      tableModel.setValueAt(contact.getEmail() == null ? "N/A" : contact.getEmail(), i, 2);
      tableModel.setValueAt(participatingContacts.contains(contact), i, 3);
    }
  }
}
