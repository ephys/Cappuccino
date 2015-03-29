package paoo.cappuccino.ihm.participationsearching;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.companydetails.CompanyDetailsView;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ParticipationSearchingViewController extends JPanel {

  private ParticipationSearchingModel model;
  private IGuiManager guiManager;
  private MenuModel menu;
  private IBusinessDayUcc businessDayUcc;
  private ICompanyUcc companyUcc;
  private ParticipationSearchingView view;
  private IBusinessDayDto[] businessDayDto;
  private IBusinessDayDto selectedBusinessDay;

  public ParticipationSearchingViewController(ParticipationSearchingModel model, MenuModel menu,
      IGuiManager guiManager, IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc) {

    super(new BorderLayout());
    this.model = model;
    this.businessDayUcc = businessDayUcc;
    this.companyUcc = companyUcc;
    this.guiManager = guiManager;
    view = new ParticipationSearchingView(model, companyUcc);
    JPanel panelWrapper = new JPanel(new BorderLayout());
    // log message dans console et fichier pour frame courant
    this.guiManager.getLogger().info("[ParticipationSearchingFrame]");


    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JComboBox<String> comboBox = new JComboBox<String>();


    businessDayDto = this.businessDayUcc.getBusinessDays();


    if (businessDayDto.length == 0) {

      comboBox.setEnabled(false);
      this.model.setCompanyDto(null);

    } else {

      IParticipationDto[] participationDto =
          businessDayUcc.getParticipations(businessDayDto[0].getId());
      this.model.setCompanyDto(participationDto);
    }


    for (int i = 0; i < businessDayDto.length; i++) {

      comboBox.addItem(businessDayDto[i].getEventDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
    }


    comboBox.addActionListener(e -> {

      this.selectedBusinessDay = businessDayDto[(int)comboBox.getSelectedIndex()];

      IParticipationDto[] participationDto =
          businessDayUcc.getParticipations(this.selectedBusinessDay.getId());
      this.model.setCompanyDto(participationDto);

    });

    searchingPanel.add(new JLabel("Journée : "));
    searchingPanel.add(comboBox);


    JTable table = view.getTable();
    table.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2) {

          System.out.println(table.getModel().getValueAt(0, table.getColumnCount()));
          int companyId =
              (int) table.getModel().getValueAt(table.getSelectedRow(), table.getColumnCount());
          ICompanyDto companyDto =
              ParticipationSearchingViewController.this.companyUcc.getCompanyById(companyId);

          ParticipationSearchingViewController.this.remove(panelWrapper);
          ParticipationSearchingViewController.this.add(new CompanyDetailsView(companyDto));
          ParticipationSearchingViewController.this.revalidate();
          ParticipationSearchingViewController.this.repaint();
        }

      }
    });

    panelWrapper.add(view);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);


  }
}
