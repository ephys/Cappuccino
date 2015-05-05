package paoo.cappuccino.ihm.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.searchcompanies.CompaniesSearchModel;
import paoo.cappuccino.ihm.searchparticipations.ParticipationSearchModel;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

public class JTableCompaniesViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = -6261431833676612196L;
  private final DefaultTableModel tableModel;
  private final JScrollPane scrollPane;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  private final ICompanyUcc companyUcc;
  private final IUserUcc userUcc;

  private final BaseModel model;

  /**
   * Creates a view for the company search screen.
   *
   * @param menu the model of the main menu to change page.
   * @param model The model of the view.
   * @param companyUcc The app instance of the company ucc.
   * @param userUcc The app instance of the user ucc.
   */
  public JTableCompaniesViewController(MenuModel menu, BaseModel model, ICompanyUcc companyUcc,
      IUserUcc userUcc) {

    this.companyUcc = companyUcc;
    this.userUcc = userUcc;

    this.model = model;

    setLayout(new BorderLayout());
    String[] tableTitles =
        new String[] {"Nom entreprise", "Adresse entreprise", "Date d'enregistrement",
            "Enregistreur"};
    this.tableModel = new DefaultTableModel(tableTitles, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    this.table = new JTable(tableModel);
    this.table.setRowHeight(35);
    this.table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company = (ICompanyDto) tableModel.getValueAt(table.getSelectedRow(), 0);
          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    TableColumn companyCol = table.getColumn(tableTitles[0]);
    companyCol.setCellRenderer(new CompanyCellRenderer());

    TableColumn dateCol = table.getColumn(tableTitles[2]);
    dateCol.setCellRenderer(new DateCellRenderer());
    dateCol.setMaxWidth(dateCol.getMaxWidth() / 3);

    TableColumn enrCol = table.getColumn(tableTitles[3]);
    enrCol.setMaxWidth(enrCol.getMaxWidth() / 3);

    this.scrollPane = new JScrollPane(table);
    scrollPane.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP, IhmConstants.M_GAP));
    this.add(scrollPane);
    model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    List<ICompanyDto> companies = null;

    if (model instanceof CompaniesSearchModel) {
      CompaniesSearchModel modelC = (CompaniesSearchModel) model;
      companies =
          companyUcc.searchCompanies(modelC.getName(), modelC.getPostCode(), modelC.getTown(),
              modelC.getStreet());
    } else if (model instanceof ParticipationSearchModel) {
      companies =
          companyUcc.getCompaniesByDay(((ParticipationSearchModel) model).getSelectedDay().getId());
    }

    buildTable(companies);
    if (table.getRowCount() > 0) {
      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);

        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }


    } else {
      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));

        centerPadding.add(new JLabel(IhmConstants.ERROR_NO_BUSINESS_DAY));

        this.remove(this.scrollPane);
        this.add(centerPadding);

        this.removedWidget = true;
        this.revalidate();
        this.repaint();
      }
    }
  }

  private void buildTable(List<ICompanyDto> companies) {
    if (companies == null) {
      return;
    }
    tableModel.setRowCount(companies.size());

    for (int i = 0; i < companies.size(); i++) {
      ICompanyDto company = companies.get(i);
      IUserDto creator = userUcc.getUserById(company.getCreator());
      String creatorName = creator == null ? "inconnu" : creator.getUsername();

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(LocalizationUtil.localizeAddress(company), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(creatorName, i, 3);
    }
  }
}
