package paoo.cappuccino.ihm.companyselection;

import java.awt.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;

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

    if (model.getCompanyDto() != null && model.getCompanyDto().length > 0 && table.getRowCount() == 0) {

      table.setModel(new tableModel(model.getCompanyDto()));

      return;

    } else if (model.getCompanyDto() == null || model.getCompanyDto().length == 0) {

      JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
     
      centerPadding.add(new JLabel(errorMessage(model.getCompanyDto())));

      this.add(centerPadding);
      return;
    }

    boolean selectAll = model.getSelectAll();

    for (int i = 0; i < table.getRowCount(); i++) {

      table.getModel().setValueAt(selectAll, i, table.getColumnCount() - 1);

    }
    table.repaint();

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
            new Object[]{companyDto[i].getName(), companyDto[i].getAddressTown(),
                         companyDto[i].getRegisterDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy à hh-mm-ss")), false};
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

      if (columnIndex == data[0].length - 1) {
        return true;
      }
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
  
  public String errorMessage(ICompanyDto[] companyDto){
    
    if(companyDto == null) return "Il n'y a aucune journée d'entreprise disponible.";
    else return "Il n'y a pas d'entreprise disponible.";
}
}
