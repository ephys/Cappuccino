package paoo.cappuccino.ihm.companyselection;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ihm.util.exception.GuiException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * ViewController for company selection gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class CompanySelectionViewController extends JPanel {

  private static final int MAX_STRING_DIRECTORY = 50;
  private CompanySelectionModel model;
  private IGuiManager guiManager;
  private File directory = new File(new JFileChooser().getFileSystemView().getDefaultDirectory()
                                        .toString());
  private CompanySelectionView view;
  private MenuModel menu;
  private IBusinessDayUcc businessDayUcc;
  private ICompanyUcc companyUcc;
  private IBusinessDayDto selectedBusinessDay;
  private IBusinessDayDto[] businessDayDto;

  public CompanySelectionViewController(CompanySelectionModel model, MenuModel menu,
                                        IGuiManager guiManager, IBusinessDayUcc businessDayUcc,
                                        ICompanyUcc companyUcc) {

    super(new BorderLayout());
    this.model = model;
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;
    this.guiManager = guiManager;
    this.menu = menu;
    view = new CompanySelectionView(model);
    // log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[SelectionCompanyFrame]");

    JPanel southPanel = new JPanel(new GridLayout(2, 1));
    JPanel savePanel = new JPanel();
    JPanel validatePanel = new JPanel();
    JFileChooser fc = new JFileChooser();
    File file = fc.getCurrentDirectory();
    JLabel directoryLocation = new JLabel(file.getAbsolutePath());
    JButton saveButton = new JButton("Valider");
    JCheckBox selectAll = new JCheckBox("Tout cocher");
    JButton directorySaveButton = new JButton("Parcourir");
  
    businessDayDto = this.businessDayUcc.getInvitationlessDays();
    
    JComboDay comboBox = new JComboDay(businessDayDto);
    
    ICompanyDto[] companyDto = this.companyUcc.getInvitableCompanies();

    saveButton.addActionListener(e -> {

      this.selectedBusinessDay = businessDayDto[(int) comboBox.getCombo().getSelectedIndex()];

      ArrayList<Integer> list = new ArrayList<Integer>();

      JTable table = view.getTable();
      for (int i = 0; i < table.getRowCount(); i++) {
        if ((boolean) table.getValueAt(i, table.getColumnCount() - 1)) {

          list.add(i);
        }

      }
      if (list.isEmpty()) {

        JOptionPane.showMessageDialog(CompanySelectionViewController.this,
                                      "Vous devez au moins selectionner une entreprise !", "Erreur",
                                      JOptionPane.ERROR_MESSAGE);
      } else {
        ICompanyDto[] companyDtoArray = new ICompanyDto[list.size()];

        BufferedWriter fileWriter = null;
        if (!directory.exists()) {
          throw new GuiException(
              "Le fichier csv n'a pas pu être enregistré, le repertoire spécifié n'existe pas !");
        }
        try {

          File saveDirectory =
              new File(directory, selectedBusinessDay.getEventDate()
                                      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
                                  + ".csv");
          if (!saveDirectory.exists()) {
            saveDirectory.createNewFile();
          }

          fileWriter = new BufferedWriter(new FileWriter(saveDirectory));

          fileWriter.write("Nom entreprise");
          fileWriter.newLine();
          fileWriter.newLine();

          for (int i = 0; i < companyDtoArray.length; i++) {

            companyDtoArray[i] = companyDto[list.get(i)];
            fileWriter.write(companyDto[list.get(i)].getName());
            fileWriter.newLine();

          }
          this.businessDayUcc.addInvitedCompanies(companyDtoArray, selectedBusinessDay);

          JOptionPane.showMessageDialog(CompanySelectionViewController.this,
                                        "Le fichier a bien pu être sauvé.");
          this.menu.setCurrentPage(MenuEntry.HOME);
        } catch (Exception exception) {

          throw new GuiException("Une erreur s'est produite lors de l'écriture dans le fichier !");
        } finally {
          try {
            fileWriter.close();
          } catch (Exception e1) {

            throw new GuiException("Une erreur s'est produite lors de la fermeture du fichier !");
          }
        }
      }

    });

    selectAll.addItemListener(e -> {

      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.model.setSelectAll(true);
      } else {

        this.model.setSelectAll(false);

      }

      if (this.model.getNotDeselectAll()) {
        this.model.setNotDeselectAll(false);
      }
    });

    if (businessDayDto.length == 0) {

      selectAll.setEnabled(false);
      saveButton.setEnabled(false);
      directorySaveButton.setEnabled(false);
      this.model.setCompanyDto(null);

    } else {
      this.model.setCompanyDto(companyDto);
    }

    savePanel.add(new JLabel("Emplacement de sauvegarde : "));
    savePanel.add(directoryLocation);

    directorySaveButton.addActionListener(e -> {

      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fc.showSaveDialog(CompanySelectionViewController.this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        directory = fc.getSelectedFile();
        String strDirectory = directory.getAbsolutePath();
        strDirectory =
            strDirectory.length() > MAX_STRING_DIRECTORY ?
            strDirectory.substring(0, MAX_STRING_DIRECTORY) + "..." : strDirectory;
        directoryLocation.setText(strDirectory);

      }
    });

    savePanel.add(directorySaveButton);

    validatePanel.add(comboBox);
    validatePanel.add(saveButton);

    southPanel.add(savePanel);
    southPanel.add(validatePanel);

    JPanel leftPadding = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    leftPadding.add(selectAll);
    this.add(leftPadding, BorderLayout.NORTH);

    JTable table = view.getTable();
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        int checkBoxCounter = 0;
        for (int i = 0; i < table.getRowCount(); i++) {

          if ((boolean) table.getModel().getValueAt(i, table.getColumnCount() - 1)) {

            checkBoxCounter++;
          }

        }

        if (model.getSelectAll() && checkBoxCounter == (table.getRowCount() - 1)) {

          model.setNotDeselectAll(true);
          selectAll.setSelected(false);
          return;
        }
        if (!model.getSelectAll() && checkBoxCounter == table.getRowCount()) {

          model.setNotDeselectAll(true);
          selectAll.setSelected(true);
          return;
        }
      }
    });

    this.add(view);
    this.add(southPanel, BorderLayout.SOUTH);


  }

}
