package paoo.cappuccino.ihm.companyselection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * View for the company selection Gui.
 *
 * @author Maduka Junior
 */
@SuppressWarnings("serial")
public class CompanySelectionView extends JPanel implements ChangeListener {

  private CompanySelectionModel model;
  private JTable table;

  public CompanySelectionView(CompanySelectionModel model) {

    setLayout(new BorderLayout());
    this.model = model;
    table = new JTable();
    this.add(new JScrollPane(table));
    this.model.addChangeListener(this);
  }


  @Override
  public void stateChanged(ChangeEvent event) {


    if (model.getCompanyDto() != null && table.getRowCount() == 0) {

      table.setModel(new tableModel(model.getCompanyDto()));

      table.getColumnModel().getColumn(table.getColumnCount() - 1).setPreferredWidth(1);
      return;

    } else if (model.getCompanyDto() == null) {

      JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
      centerPadding.add(new JLabel(
          "Ce message vous est affiché : soit parce qu'il n'y a plus d'entreprise disponible, soit parce "
              + "qu'il n'y a plus de journée d'enteprise."));

      this.add(centerPadding);
      return;
    }

    boolean selectAll = model.getSelectAll();

    for (int i = 0; i < table.getRowCount(); i++) {

      table.setValueAt(selectAll, i, table.getColumnCount() - 1);
      table.repaint();
    }


  }


  public JTable getTable() {

    return table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement",
    "Selectionner"};
    Object[][] data;

    public tableModel(ICompanyDto[] companyDto) {

      data = new Object[companyDto.length][];

      for (int i = 0; i < companyDto.length; i++) {

        data[i] =
            new Object[] {companyDto[i].getName(), companyDto[i].getAddressTown(),
            companyDto[i].getRegisterDate().toString(), false};
      }

    }

    @Override
    public int getRowCount() {
      return data.length;
    }

    @Override
    public int getColumnCount() {
      return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
      return columns[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

      if (columnIndex == data[0].length - 1)
        return true;
      return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

      data[rowIndex][columnIndex] = aValue;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return data[0][columnIndex].getClass();
    }
  }
}
