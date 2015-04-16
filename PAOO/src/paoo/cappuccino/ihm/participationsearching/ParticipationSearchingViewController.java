package paoo.cappuccino.ihm.participationsearching;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ParticipationSearchingViewController extends JPanel {

  private final ParticipationSearchingModel model;
  private final List<IBusinessDayDto> businessDayDto;

  public ParticipationSearchingViewController(ParticipationSearchingModel model, MenuModel menu,
                                              IBusinessDayUcc businessDayUcc,
                                              ICompanyUcc companyUcc) {

    super(new BorderLayout());
    this.model = model;

    ParticipationSearchingView view = new ParticipationSearchingView(model, companyUcc, businessDayUcc);
    JPanel panelWrapper = new JPanel(new BorderLayout());

    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    businessDayDto = businessDayUcc.getBusinessDays();

    JComboDay comboBox = new JComboDay(
        businessDayDto.toArray(new IBusinessDayDto[businessDayDto.size()]));

    if (businessDayDto.size() == 0) {
      comboBox.setEnabled(false);
    } else {
      this.model.setSelectedDay(businessDayDto.get(0));
    }

    comboBox.getCombo().addActionListener(e -> {
      IBusinessDayDto selectedBusinessDay = businessDayDto.get(comboBox.getCombo().getSelectedIndex());

      this.model.setSelectedDay(selectedBusinessDay);
    });

    searchingPanel.add(comboBox);

    JTable table = view.getTable();
    table.setRowHeight(35);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          ICompanyDto company =
              (ICompanyDto) table.getModel().getValueAt(table.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    panelWrapper.add(view);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
  }
}
