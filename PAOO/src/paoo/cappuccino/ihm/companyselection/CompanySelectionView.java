package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * View for the company selection Gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionView extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = -5537446066860286864L;
  private final JTable companiesTable;

  /**
   * Creates a new view for the company selection screen.
   *
   * @param model The model of the view.
   */
  @SuppressWarnings("serial")
  public CompanySelectionView(CompanySelectionModel model, ICompanyUcc companyUcc) {
    setLayout(new BorderLayout());

    String[] tableNames =
        {"Nom entreprise", "Adresse entreprise", "Date d'enregistrement", "Sélectionner"};

    List<ICompanyDto> invitableCompanies = companyUcc.getInvitableCompanies();
    DefaultTableModel tableModel = new DefaultTableModel(tableNames, invitableCompanies.size()) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 3;
      }
    };

    companiesTable = new JTable(tableModel) {
      @Override
      public Class getColumnClass(int column) {
        switch (column) {
          case 0:
            return ICompanyDto.class;
          case 1:
            return String.class;
          case 2:
            return LocalDateTime.class;
          case 3:
            return Boolean.class;
          default:
            throw new IllegalArgumentException(column + " > 3 | < 0");
        }
      }
    };

    companiesTable.setRowHeight(35);

    for (int i = 0; i < invitableCompanies.size(); i++) {
      ICompanyDto company = invitableCompanies.get(i);

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(LocalizationUtil.localizeAddress(company), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(false, i, 3);
    }

    TableColumn dateCol = companiesTable.getColumn(tableNames[2]);
    dateCol.setCellRenderer(new DateCellRenderer());
    dateCol.setMaxWidth(dateCol.getMaxWidth() / 4);

    TableColumn selectCol = companiesTable.getColumn(tableNames[3]);
    selectCol.setMaxWidth(selectCol.getMaxWidth() / 4);

    companiesTable.getColumn(tableNames[0]).setCellRenderer(new CompanyCellRenderer());

    ChangeListener listener =
        e -> {
          this.removeAll();
          if (model.getSelectedDay() != null) {
            JScrollPane scrollPane = new JScrollPane(companiesTable);
            scrollPane.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
                IhmConstants.M_GAP, IhmConstants.M_GAP));
            this.add(scrollPane);
          } else {
            this.add(new JLabel(IhmConstants.SELECT_A_DAY, SwingConstants.CENTER));
          }

          this.repaint();
          this.revalidate();
        };

    model.addChangeListener(listener);
    listener.stateChanged(null);
  }

  JTable getCompaniesTable() {
    return companiesTable;
  }
}
