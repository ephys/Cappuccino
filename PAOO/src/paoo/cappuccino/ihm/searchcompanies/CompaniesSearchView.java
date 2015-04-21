package paoo.cappuccino.ihm.searchcompanies;

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
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IUserUcc;

@SuppressWarnings("serial")
public class CompaniesSearchView extends JPanel implements ChangeListener {

  private final CompaniesSearchModel model;
  private final ICompanyUcc companyUcc;
  private final IUserUcc userUcc;
  private final DefaultTableModel tableModel;
  private final JScrollPane scrollPane;
  private final JTable table;
  private boolean removedWidget;
  private JPanel centerPadding;

  /**
   * Creates a view for the company search screen.
   *
   * @param model      The model of the view.
   * @param companyUcc The app instance of the company ucc.
   * @param userUcc    The app instance of the user ucc.
   */
  public CompaniesSearchView(CompaniesSearchModel model, ICompanyUcc companyUcc, IUserUcc userUcc) {

    setLayout(new BorderLayout());
    this.model = model;
    this.companyUcc = companyUcc;
    this.userUcc = userUcc;

    String[] tableTitles = new String[]{"Nom entreprise", "Adresse entreprise",
                                        "Date d'enregistrement", "Enregistreur"};
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

    this.scrollPane = new JScrollPane(table);
    this.add(scrollPane);
    this.model.addChangeListener(this);
    stateChanged(null);
  }

  @Override
  public void stateChanged(ChangeEvent event) {

    List<ICompanyDto> companies =
        companyUcc.searchCompanies(model.getName(), model.getPostCode(), model.getTown(),
                                   model.getStreet());

    if (companies != null && companies.size() != 0) {
      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);

        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }

      buildTable(companies);
    } else {
      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));

        centerPadding.add(new JLabel("Il n'y a aucune entreprise correspondante"));

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

  private void buildTable(List<ICompanyDto> companies) {
    tableModel.setRowCount(companies.size());

    for (int i = 0; i < companies.size(); i++) {
      ICompanyDto company = companies.get(i);
      IUserDto creator = userUcc.getUserById(company.getCreator());
      String creatorName = "inconnu";
      if (creator != null) {
        creatorName = creator.getUsername();
      }

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(LocalizationUtil.localizeAddress(company), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(creatorName, i, 3);
    }
  }
}
