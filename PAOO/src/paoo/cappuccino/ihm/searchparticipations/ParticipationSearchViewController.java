package paoo.cappuccino.ihm.searchparticipations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

public class ParticipationSearchViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 7264715733673092584L;
  private final ParticipationSearchModel model;
  private final List<IBusinessDayDto> listBusinessDayDto;
  private final IBusinessDayUcc businessDayUcc;
  private final ICompanyUcc companyUcc;
  private ParticipationSearchView view;

  /**
   * Creates a view controller for the participation search view.
   *
   * @param model The model of the view.
   * @param menu The model of the menu.
   * @param businessDayUcc The app instance of the business day ucc.
   * @param companyUcc The app instance of the company ucc.
   */
  public ParticipationSearchViewController(ParticipationSearchModel model, MenuModel menu,
      IBusinessDayUcc businessDayUcc, ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.model = model;
    this.companyUcc = companyUcc;
    this.businessDayUcc = businessDayUcc;

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

    view = new ParticipationSearchView();
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
    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    List<IParticipationDto> participations =
        model.getSelectedDay() == null ? null : businessDayUcc.getParticipations(model
            .getSelectedDay().getId());
    buildTable(participations);
    view.stateChanged(null);
  }

  private void buildTable(List<IParticipationDto> participations) {
    DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
    tableModel.setRowCount(participations.size());

    for (int i = 0; i < participations.size(); i++) {
      IParticipationDto participation = participations.get(i);
      ICompanyDto company = companyUcc.getCompanyById(participation.getCompany());

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(LocalizationUtil.localizeAddress(company), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(participation.getState(), i, 3);
    }
  }
}
