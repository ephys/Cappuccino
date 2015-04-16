package paoo.cappuccino.ihm.participationsearch;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.StateCellRenderer;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ParticipationSearchView extends JPanel implements ChangeListener {

  private final ParticipationSearchModel model;
  private final ICompanyUcc companyUcc;
  private final DefaultTableModel tableModel;
  private final JScrollPane scrollPane;
  private final IBusinessDayUcc businessDayUcc;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  /**
   * Creates a view for the participation search screen.
   * @param model The model of the view.
   * @param companyUcc The app instance of the company ucc.
   * @param businessDayUcc The app instance of the business day ucc.
   */
  public ParticipationSearchView(ParticipationSearchModel model, ICompanyUcc companyUcc,
                                 IBusinessDayUcc businessDayUcc) {
    this.businessDayUcc = businessDayUcc;

    setLayout(new BorderLayout());
    this.model = model;
    this.companyUcc = companyUcc;

    String[] tableTitles = new String[] {"Nom entreprise", "Adresse entreprise",
                                         "Date d'enregistrement", "État"};
    this.tableModel = new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.table = new JTable(tableModel);

    TableColumn companyCol = table.getColumn(tableTitles[0]);
    companyCol.setCellRenderer(new CompanyCellRenderer());

    TableColumn dateCol = table.getColumn(tableTitles[2]);
    dateCol.setCellRenderer(new DateCellRenderer());

    TableColumn stateCol = table.getColumn(tableTitles[3]);
    stateCol.setCellRenderer(new StateCellRenderer());

    this.scrollPane = new JScrollPane(table);
    this.add(scrollPane);
    this.model.addChangeListener(this);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    List<IParticipationDto> participations =
        model.getSelectedDay() == null
        ? null : businessDayUcc.getParticipations(model.getSelectedDay().getId());

    if (participations != null && participations.size() != 0) {
      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);
        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }

      buildTable(participations);
    } else {
      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));

        centerPadding.add(new JLabel(buildErrorMessage(participations)));

        this.remove(this.scrollPane);
        this.add(centerPadding);
        this.removedWidget = true;
        this.revalidate();
        this.repaint();
      }
    }
  }

  JTable getTable() {
    return this.table;
  }

  private void buildTable(List<IParticipationDto> participations) {
    tableModel.setRowCount(participations.size());

    for (int i = 0; i < participations.size(); i++) {
      IParticipationDto participation = participations.get(i);
      ICompanyDto company = companyUcc.getCompanyById(participation.getCompany());

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(company.getAddressTown(), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(participation.getState(), i, 3);
    }
  }

  private String buildErrorMessage(List<IParticipationDto> participationDto) {
    if (participationDto == null) {
      return "Il n'y a aucune journée d'entreprise disponible.";
    } else {
      return "Il n'y a aucune participation correspondante.";
    }
  }
}
