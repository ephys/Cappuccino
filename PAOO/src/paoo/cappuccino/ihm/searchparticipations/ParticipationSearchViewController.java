package paoo.cappuccino.ihm.searchparticipations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ihm.util.JTableCompaniesViewController;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

public class ParticipationSearchViewController extends JPanel {

  private static final long serialVersionUID = 7264715733673092584L;
  private final ParticipationSearchModel model;
  private final List<IBusinessDayDto> listBusinessDayDto;

  /**
   * Creates a view controller for the participation search view.
   *
   * @param model The model of the view.
   * @param menu The model of the menu.
   * @param businessDayUcc The app instance of the business day ucc.
   * @param companyUcc The app instance of the company ucc.
   */
  public ParticipationSearchViewController(ParticipationSearchModel model, MenuModel menu,
      IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc, IUserUcc userUcc) {
    super(new BorderLayout());
    this.model = model;

    listBusinessDayDto = businessDayUcc.getBusinessDays();
    JComboDay comboBox =
        new JComboDay(listBusinessDayDto.toArray(new IBusinessDayDto[listBusinessDayDto.size()]),
            menu);

    if (listBusinessDayDto.size() != 0) {
      this.model.setSelectedDay(listBusinessDayDto.get(0));
    }

    comboBox.getCombo().addActionListener(
        e -> {
          IBusinessDayDto selectedBusinessDay =
              listBusinessDayDto.get(comboBox.getCombo().getSelectedIndex());

          this.model.setSelectedDay(selectedBusinessDay);
        });

    JPanel searchingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchingPanel.add(comboBox);

    JTableCompaniesViewController viewController =
        new JTableCompaniesViewController(menu, model, companyUcc, userUcc);


    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.add(viewController);
    panelWrapper.add(searchingPanel, BorderLayout.NORTH);
    this.add(panelWrapper);
  }
}
