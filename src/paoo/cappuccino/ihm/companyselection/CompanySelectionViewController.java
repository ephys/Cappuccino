package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.IDefaultButtonHandler;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

/**
 * ViewController for company selection gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionViewController extends JPanel implements IDefaultButtonHandler {

  private static final long serialVersionUID = -6736440076463819872L;
  private static final Pattern csvIllegalCharRegex = Pattern.compile("(;|\"|\\n)", Pattern.DOTALL);
  private static final int MAX_STRING_DIRECTORY = 50;

  private final CompanySelectionModel model;
  private final MenuModel menu;
  private final IBusinessDayUcc businessDayUcc;
  private final IContactUcc contactUcc;
  private final Logger logger;
  private final JComboBox<IBusinessDayDto> businessDaySelector;
  private final JButton saveButton;

  private File destinationDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory();

  /**
   * Creates a new view controller for the company selection screen.
   *
   * @param model The model of the view controller.
   * @param menu The model of the menu.
   * @param businessDayUcc The app intance of the business day ucc.
   * @param companyUcc The app instance of the company ucc.
   */
  public CompanySelectionViewController(CompanySelectionModel model, MenuModel menu,
      IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc, IGuiManager guiManager,
      IContactUcc contactUcc) {
    super(new BorderLayout());
    this.model = model;
    this.menu = menu;
    this.businessDayUcc = businessDayUcc;
    this.contactUcc = contactUcc;
    this.logger = guiManager.getLogger();

    CompanySelectionView view = new CompanySelectionView(model, companyUcc);
    JTable companiesTable = view.getCompaniesTable();
    JCheckBox selectAllButton = new JCheckBox("Tout cocher");
    selectAllButton.addActionListener(e -> {
      final boolean selectAll = selectAllButton.isSelected();

      for (int i = 0; i < companiesTable.getRowCount(); i++) {
        companiesTable.setValueAt(selectAll, i, 3);
      }
    });

    List<IBusinessDayDto> invitationlessDays = businessDayUcc.getInvitationlessDays();
    JComboDay jcomboDay =
        new JComboDay(invitationlessDays.toArray(new IBusinessDayDto[invitationlessDays.size()]),
            menu);
    this.businessDaySelector = jcomboDay.getCombo();
    businessDaySelector.addActionListener(event -> {
      if (model.getSelectedDay() != businessDaySelector.getSelectedItem()) {
        model.setSelectedDay((IBusinessDayDto) businessDaySelector.getSelectedItem());
      }
    });

    saveButton = new JButton("Sauvegarder et Valider");
    saveButton.addActionListener(e -> {
      List<ICompanyDto> selectedCompanies = new ArrayList<>();
      for (int i = 0; i < companiesTable.getRowCount(); i++) {
        if ((boolean) companiesTable.getValueAt(i, 3)) {
          selectedCompanies.add((ICompanyDto) companiesTable.getValueAt(i, 0));
        }
      }

      if (selectedCompanies.isEmpty()) {
        JOptionPane.showMessageDialog(CompanySelectionViewController.this,
            "Vous devez au moins selectionner une entreprise !", "Sélection invalide",
            JOptionPane.INFORMATION_MESSAGE);

        return;
      }

      if (writeCompaniesToDisk(selectedCompanies)) {
        this.businessDayUcc.addInvitedCompanies(
            selectedCompanies.toArray(new ICompanyDto[selectedCompanies.size()]),
            model.getSelectedDay());

        IBusinessDayDto selectedDay = model.getSelectedDay();
        model.setSelectedDay(null);
        this.menu.setCurrentPage(MenuEntry.HOME, selectedDay);
      }
    });

    JLabel directoryLocation = new JLabel(destinationDirectory.getAbsolutePath());
    JPanel savePanel = new JPanel();
    savePanel.add(new JLabel("Emplacement de sauvegarde : "));
    savePanel.add(directoryLocation);

    JButton directorySaveButton = new JButton("Parcourir");
    directorySaveButton.addActionListener(e -> {
      JFileChooser fc = new JFileChooser();
      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fc.showSaveDialog(CompanySelectionViewController.this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        destinationDirectory = fc.getSelectedFile();
        String strDirectory = destinationDirectory.getAbsolutePath();
        strDirectory =
            strDirectory.length() > MAX_STRING_DIRECTORY ? strDirectory.substring(0,
                MAX_STRING_DIRECTORY) + "..." : strDirectory;
        directoryLocation.setText(strDirectory);
      }
    });

    savePanel.add(directorySaveButton);

    JPanel validatePanel = new JPanel();
    validatePanel.add(jcomboDay);
    validatePanel.add(saveButton);

    JPanel southPanel = new JPanel(new GridLayout(2, 1));
    southPanel.add(savePanel);
    southPanel.add(validatePanel);

    JPanel leftPadding = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    leftPadding.add(selectAllButton);
    this.add(leftPadding, BorderLayout.NORTH);

    companiesTable.getModel().addTableModelListener(
        e -> {
          if (companiesTable.getRowCount() == 0
              || companiesTable.getValueAt(companiesTable.getRowCount() - 1, 3) == null) {
            return;
          }

          if (e.getColumn() != 3) {
            return;
          }

          for (int i = 0; i < companiesTable.getRowCount(); i++) {
            if (!(boolean) companiesTable.getValueAt(i, 3)) {
              selectAllButton.setSelected(false);
              return;
            }

            selectAllButton.setSelected(true);
          }
        });
    companiesTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company =
              (ICompanyDto) companiesTable.getModel()
                  .getValueAt(companiesTable.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    this.add(view);
    this.add(southPanel, BorderLayout.SOUTH);

    ChangeListener changeListener = e -> {
      final boolean visible = model.getSelectedDay() != null;
      selectAllButton.setVisible(visible);
      saveButton.setVisible(visible);
      savePanel.setVisible(visible);

      businessDaySelector.setSelectedItem(model.getSelectedDay());
    };
    model.addChangeListener(changeListener);
    changeListener.stateChanged(null);
  }

  private String escapeCsv(String str) {
    if (csvIllegalCharRegex.matcher(str).find()) {
      return "\"" + str + "\"";
    }

    return str;
  }

  private boolean writeCompaniesToDisk(List<ICompanyDto> selectedCompanies) {
    File saveDirectory =
        new File(destinationDirectory, model.getSelectedDay().getEventDate()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            + ".csv");

    while (saveDirectory.exists()) {
      saveDirectory = new File(saveDirectory.getPath() + ".dup");
    }

    try {
      final File directory = saveDirectory.getParentFile();
      if (!directory.exists() && !directory.mkdirs()) {
        JOptionPane.showMessageDialog(CompanySelectionViewController.this,
            "Impossible de crÃ©er le rÃ©pertoire du fichier " + directory.getAbsolutePath(),
            "Erreur filesystem", JOptionPane.ERROR_MESSAGE);

        return false;
      }

      if (!saveDirectory.createNewFile()) {
        JOptionPane.showMessageDialog(CompanySelectionViewController.this,
            "Impossible de crÃ©er le fichier " + saveDirectory.getAbsolutePath(),
            "Erreur filesystem", JOptionPane.ERROR_MESSAGE);

        return false;
      }

      try (BufferedWriter fileWriter =
          new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveDirectory),
              Charset.forName("UTF-8")))) {
        fileWriter.write("Nom entreprise;Nom;PrÃ©nom;Email");
        fileWriter.newLine();
        fileWriter.newLine();

        for (ICompanyDto company : selectedCompanies) {
          List<IContactDto> companyContacts = contactUcc.getContactByCompany(company.getId());

          for (IContactDto contact : companyContacts) {
            if (!contact.isEmailValid() || contact.getEmail() == null) {
              continue;
            }

            fileWriter.write(escapeCsv(company.getName()) + ";" + escapeCsv(contact.getLastName())
                + ";" + escapeCsv(contact.getFirstName()) + ";" + escapeCsv(contact.getEmail()));
            fileWriter.newLine();
          }

          if (companyContacts.isEmpty()) {
            fileWriter.write(escapeCsv(company.getName()) + ";N/A;N/A;N/A");
            fileWriter.newLine();
          }
        }
      }

      return true;
    } catch (IOException e) {
      logger.log(Level.WARNING, "Could not create CSV file", e);

      JOptionPane.showMessageDialog(CompanySelectionViewController.this,
          "Impossible de crÃ©er le fichier " + saveDirectory.getAbsolutePath(),
          "Erreur filesystem", JOptionPane.ERROR_MESSAGE);

      return false;
    }
  }

  @Override
  public JButton getSubmitButton() {
    return saveButton;
  }
}
