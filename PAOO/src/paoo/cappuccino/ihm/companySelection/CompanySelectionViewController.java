package paoo.cappuccino.ihm.companySelection;

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

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.menu.MenuViewController;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;



public class CompanySelectionViewController extends JPanel{
 
  private CompanySelectionModel model;
  private MenuModel menu;
  private IGuiManager guiManager;
  private File directory;
  private MenuViewController controller;
  private CompanySelectionView view;
  
  
  public CompanySelectionViewController(CompanySelectionModel model, MenuModel menu, IGuiManager guiManager, MenuViewController controller) {
    
    super(new BorderLayout());
    this.model = model;
    this.menu = menu;
    this.guiManager = guiManager;
    this.controller = controller;
    this.view = new CompanySelectionView(model);
    //log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[SelectionCompanyFrame]");
     
    //a voir
    JPanel viewWrapper = new JPanel();
    JPanel southPanel = new JPanel(new GridLayout(2,1));
    JPanel savePanel = new JPanel();
    JPanel validatePanel = new JPanel();
    JFileChooser fc = new JFileChooser();
    File file = fc.getCurrentDirectory();
    JLabel directoryLocation = new JLabel(file.getAbsolutePath());
    JButton saveButton = new JButton("Valider");
    
    saveButton.addActionListener(e->{
      
        // recuperer tous les personnes de contact par entreprise selectionnée + les sauver dans fichier .csv
      
    });

    saveButton.setEnabled(false);
    
    IBusinessDayUcc businessDayUcc = this.controller.getBusinessDayUcc();
    ICompanyUcc companyUcc = this.controller.getCompanyUcc();
    
    IBusinessDayDto[] businessDayDto = businessDayUcc.getInvitationlessDays();
    
    
    JCheckBox selectAll = new JCheckBox("Tout cocher");
    
    selectAll.addItemListener(e ->{
      
      if (e.getStateChange() == ItemEvent.SELECTED){
        this.model.setSelectAll(true);
        saveButton.setEnabled(true);
      }
      else{
        this.model.setSelectAll(false);
        saveButton.setEnabled(false);
      }
    });
    
    if(businessDayDto == null) selectAll.setEnabled(false);
    //a changer
    this.model.setCompanyDto(null);
   //JComboBox<IBusinessDayDto> comboBox = new JComboBox<IBusinessDayDto>(businessDayDto);
   
    //comboBox.addActionListener(e->{
      
      //String businessDaySelection = (String)comboBox.getSelectedItem();
      
     // ICompanyDto[] companyDto = companyUcc.getInvitableCompanies(businessDaySelection);  ???
     // this.model.setCompanyDto(companyDto);
      
    //});
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
       }
   );
    
     
     savePanel.add(directorySaveButton);
     
     validatePanel.add(new JLabel("Journée : "));
     //validatePanel.add(comboBox);
     validatePanel.add(saveButton);
     
     southPanel.add(savePanel);
     southPanel.add(validatePanel);
     
     JPanel leftPadding = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     leftPadding.add(selectAll);
     this.add(leftPadding,BorderLayout.NORTH);
     viewWrapper.addMouseListener(new MouseAdapter(){
        
       public void mouseClicked(MouseEvent e){
         
         JTable table = CompanySelectionViewController.this.view.getTable();
         
         for(int i = 0; i < table.getRowCount();i++){
           
            if((boolean)table.getValueAt(i, table.getColumnCount()-1)){             
              saveButton.setEnabled(true);
               return;
            }
           
         }
         
       }
     });
     viewWrapper.add(this.view);
     this.add(this.view);
     this.add(southPanel,BorderLayout.SOUTH);
    
    
  }
  
}
