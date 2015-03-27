package paoo.cappuccino.ihm.participationsearching;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.IBusinessDayUcc;

@SuppressWarnings("serial")
public class ParticipationSearchingViewController extends JPanel {

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
    view = new ParticipationSearchingView(model);
    // log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[ParticipationSearchingFrame]");


    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JComboBox<String> comboBox = new JComboBox<String>();


    businessDayDto = businessDayUcc.getBusinessDays();


    if (businessDayDto.length == 0) {

      comboBox.setEnabled(false);
      this.model.setCompanyDto(null);

    } else {
    //TODO
     // ICompanyDto[] companyDto = businessDayUcc.getAttendingCompanies(businessDayDto[0].getId());
      this.model.setCompanyDto(null);
    }


    for (int i = 0; i < businessDayDto.length; i++) {

      comboBox.addItem(businessDayDto[i].getEventDate().toString());
    }


    comboBox.addActionListener(e -> {

      String selectedItem = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
      matchingBusinessDay(selectedItem);

      //TODO
     // ICompanyDto[] companyDto = businessDayUcc.getAttendingCompanies(selectedBusinessDay.getId());
      this.model.setCompanyDto(null);

    });

    searchingPanel.add(new JLabel("Journée : "));
    searchingPanel.add(comboBox);


    JTable table = view.getTable();
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        // TODO inner ihm

      }
    });

    this.add(view);
    this.add(searchingPanel, BorderLayout.NORTH);


  }

  public void matchingBusinessDay(String selectedItem) {

    for (int i = 0; i < businessDayDto.length; i++) {

      if (businessDayDto[i].getEventDate().toString().equals(selectedItem)) {

        selectedBusinessDay = businessDayDto[i];
        break;
      }

    }
  }
}
