package paoo.cappuccino.ihm.participationsearch;

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
public class ParticipationSearchViewController extends JPanel {

  private final ParticipationSearchModel model;
  private final List<IBusinessDayDto> businessDayDto;

  /**
   * Creates a view controller for the participation search view.
   *
   * @param model          The model of the view.
   * @param menu           The model of the menu.
   * @param businessDayUcc The app instance of the business day ucc.
   * @param companyUcc     The app instance of the company ucc.
   */
  public ParticipationSearchViewController(ParticipationSearchModel model, MenuModel menu,
                                           IBusinessDayUcc businessDayUcc,
                                           ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.model = model;

    businessDayDto = businessDayUcc.getBusinessDays();

    JComboDay comboBox = new JComboDay(
        businessDayDto.toArray(new IBusinessDayDto[businessDayDto.size()]));

    if (businessDayDto.size() == 0) {
      comboBox.setEnabled(false);
    } else {
      this.model.setSelectedDay(businessDayDto.get(0));
    }

    comboBox.getCombo().addActionListener(e -> {
      IBusinessDayDto selectedBusinessDay =
          businessDayDto.get(comboBox.getCombo().getSelectedIndex());

      this.model.setSelectedDay(selectedBusinessDay);
    });

    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchingPanel.add(comboBox);

    ParticipationSearchView view = new ParticipationSearchView(model, companyUcc,
                                                               businessDayUcc);
    JTable table = view.getTable();
    table.setRowHeight(35);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company =
              (ICompanyDto) table.getModel().getValueAt(table.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(view);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
  }
}
