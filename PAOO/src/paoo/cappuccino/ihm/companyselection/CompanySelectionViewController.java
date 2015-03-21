package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ucc.IBusinessDayUcc;

/**
 * ViewController for company selection gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class CompanySelectionViewController extends JPanel {

  private CompanySelectionModel model;
  private IGuiManager guiManager;
  private File directory;
  private CompanySelectionView view;
  private IBusinessDayUcc businessDayUcc;


  public CompanySelectionViewController(CompanySelectionModel model, IGuiManager guiManager, IBusinessDayUcc businessDayUcc) {

    super(new BorderLayout());
    this.model = model;
    this.businessDayUcc = businessDayUcc;
    this.guiManager = guiManager;
    this.view = new CompanySelectionView(model);
    // log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[SelectionCompanyFrame]");


    JPanel southPanel = new JPanel(new GridLayout(2, 1));
    JPanel savePanel = new JPanel();
    JPanel validatePanel = new JPanel();
    JFileChooser fc = new JFileChooser();
    File file = fc.getCurrentDirectory();
    JLabel directoryLocation = new JLabel(file.getAbsolutePath());
    JButton saveButton = new JButton("Valider");

    saveButton.addActionListener(e -> {

      // recuperer tous les personnes de contact par entreprise selectionnée + les sauver dans
      // fichier .csv

      });

    saveButton.setEnabled(false);

    // IBusinessDayUcc businessDayUcc = this.controller.getBusinessDayUcc();
    // ICompanyUcc companyUcc = this.controller.getCompanyUcc();

    // verifier si c'est pas null, sinon afficher message aucun business day
    // IBusinessDayDto[] businessDayDto = businessDayUcc.getInvitationlessDays();


    JCheckBox selectAll = new JCheckBox("Tout cocher");

    selectAll.addItemListener(e -> {

      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.model.setSelectAll(true);
        saveButton.setEnabled(true);
      } else {
        this.model.setSelectAll(false);
        saveButton.setEnabled(false);
      }
    });


    // if (businessDayDto == null)
    // selectAll.setEnabled(false);


    // a changer
    this.model.setCompanyDto(null);
    // JComboBox<IBusinessDayDto> comboBox = new JComboBox<IBusinessDayDto>(businessDayDto);

    // comboBox.addActionListener(e->{

    // String businessDaySelection = (String)comboBox.getSelectedItem();

    // ICompanyDto[] companyDto = companyUcc.getInvitableCompanies();
    // if(companyDto == null) selectAll.setEnabled(false);
    // this.model.setCompanyDto(companyDto);

    // });
    savePanel.add(new JLabel("Emplacement de sauvegarde : "));
    savePanel.add(directoryLocation);
    JButton directorySaveButton = new JButton("Parcourir");

    directorySaveButton.addActionListener(e -> {

      fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = fc.showSaveDialog(CompanySelectionViewController.this);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
        directory = fc.getSelectedFile();
        directoryLocation.setText(directory.getAbsolutePath());

      } else {
        return;
      }
    });


    savePanel.add(directorySaveButton);

    validatePanel.add(new JLabel("Journée : "));

    // a enlever
    String[] string = {"dsdq", "dqsdqs", "dsqd", "dsqd", "dsqdsdqs"};
    JComboBox<String> comboBox = new JComboBox<String>(string);

    validatePanel.add(comboBox);
    validatePanel.add(saveButton);

    southPanel.add(savePanel);
    southPanel.add(validatePanel);

    JPanel leftPadding = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    leftPadding.add(selectAll);
    this.add(leftPadding, BorderLayout.NORTH);

    JTable table = this.view.getTable();
    table.addMouseListener(new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {

        for (int i = 0; i < table.getRowCount(); i++) {

          if ((boolean) table.getValueAt(i, table.getColumnCount() - 1)) {
            saveButton.setEnabled(true);
            return;
          }

        }
        // a voir
        saveButton.setEnabled(false);
      }
    });

    this.add(this.view);
    this.add(southPanel, BorderLayout.SOUTH);


  }

}
