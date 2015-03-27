package paoo.cappuccino.ihm.participationsearching;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.companyselection.CompanySelectionModel;
import paoo.cappuccino.ihm.companyselection.CompanySelectionView;
import paoo.cappuccino.ihm.companyselection.CompanySelectionViewController;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.exception.GuiException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ParticipationSearchingViewController extends JPanel{
   
  private ParticipationSearchingModel model;
  private IGuiManager guiManager;
  private MenuModel menu;
  private IBusinessDayUcc businessDayUcc;
  private ParticipationSearchingView view;
  private IBusinessDayDto[] businessDayDto;
  private IBusinessDayDto selectedBusinessDay;
  
  public ParticipationSearchingViewController(ParticipationSearchingModel model, MenuModel menu,
      IGuiManager guiManager, IBusinessDayUcc businessDayUcc) {

    super(new BorderLayout());
    this.model = model;
    this.businessDayUcc = businessDayUcc;
    this.guiManager = guiManager;
    this.view = new ParticipationSearchingView(model);
    // log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[ParticipationSearchingFrame]");


    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JComboBox<String> comboBox = new JComboBox<String>();
   
    
    businessDayDto = businessDayUcc.getBusinessDays();


    if (businessDayDto.length == 0) {
      
      comboBox.setEnabled(false);
      this.model.setCompanyDto(null);

    } else {
      
      ICompanyDto[] companyDto =  businessDayUcc.getAttendingCompanies(this.businessDayDto[0].getId());
      this.model.setCompanyDto(companyDto);
    }


    for (int i = 0; i < businessDayDto.length; i++) {

      comboBox.addItem(businessDayDto[i].getEventDate().toString());
    }


    comboBox.addActionListener(e->{
      
      String selectedItem = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
      matchingBusinessDay(selectedItem);
      
      ICompanyDto[] companyDto =  businessDayUcc.getAttendingCompanies(this.selectedBusinessDay.getId());
      this.model.setCompanyDto(companyDto);
      
    });
    
    searchingPanel.add(new JLabel("Journée : "));
    searchingPanel.add(comboBox);


    JTable table = this.view.getTable();
    table.addMouseListener(new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {

           //TODO inner ihm
        
    }
      });

    this.add(this.view);
    this.add(searchingPanel, BorderLayout.NORTH);


  }
  
  public void matchingBusinessDay(String selectedItem){
    
    for (int i = 0; i < businessDayDto.length; i++) {

      if (businessDayDto[i].getEventDate().toString().equals(selectedItem)) {

        this.selectedBusinessDay = businessDayDto[i];
        break;
      }

    }
  }
}
