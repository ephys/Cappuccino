package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.DateCellRenderer;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * View for the company selection Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class CompanySelectionView extends JPanel {

  private static final String[] tableNames = {"Nom entreprise", "Adresse entreprise",
                                              "Date d'enregistrement", "Sélectionner"};

  private final JTable companiesTable;

  /**
   * Creates a new view for the company selection screen.
   *
   * @param model The model of the view.
   */
  public CompanySelectionView(CompanySelectionModel model, ICompanyUcc companyUcc) {
    setLayout(new BorderLayout());

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

    for (int i = 0; i < invitableCompanies.size(); i++) {
      ICompanyDto company = invitableCompanies.get(i);

      tableModel.setValueAt(company, i, 0);
      tableModel.setValueAt(company.getAddressTown(), i, 1);
      tableModel.setValueAt(company.getRegisterDate(), i, 2);
      tableModel.setValueAt(false, i, 3);
    }

    companiesTable.getColumn(tableNames[0]).setCellRenderer(new CompanyCellRenderer());
    companiesTable.getColumn(tableNames[2]).setCellRenderer(new DateCellRenderer());

    ChangeListener listener = e -> {
      this.removeAll();
      if (model.getSelectedDay() != null) {
        this.add(new JScrollPane(companiesTable));
      } else {
        this.add(new JLabel("Aucune journée des entreprises sélectionnée", SwingConstants.CENTER));
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
