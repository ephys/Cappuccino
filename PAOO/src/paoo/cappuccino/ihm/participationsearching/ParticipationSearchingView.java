package paoo.cappuccino.ihm.participationsearching;

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

@SuppressWarnings("serial")
public class ParticipationSearchingView extends JPanel implements ChangeListener{

  private ParticipationSearchingModel model;
  private JTable table;

  public ParticipationSearchingView(ParticipationSearchingModel model) {

    setLayout(new BorderLayout());
    this.model = model;
    this.table = new JTable();
    this.add(new JScrollPane(table));
    this.model.addChangeListener(this);
  }


  @Override
  public void stateChanged(ChangeEvent event) {


    if (model.getCompanyDto() != null) {

      this.table.setModel(new tableModel(model.getCompanyDto()));

      return;

    } else{

      JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
      centerPadding.add(new JLabel("Il n'y a aucune journée d'entreprise de disponible."));

      this.add(centerPadding);
    }

  }


  public JTable getTable() {

    return this.table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement",
        "Selectionner"};
    Object[][] data;

    public tableModel(ICompanyDto[] companyDto) {

      this.data = new Object[companyDto.length][];

      for (int i = 0; i < companyDto.length; i++) {

        this.data[i] =
            new Object[] {companyDto[i].getName(), companyDto[i].getAddressTown(),
                companyDto[i].getRegisterDate().toString()};
      }

    }

    public int getRowCount() {
      return data.length;
    }

    public int getColumnCount() {
      return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
      return data[rowIndex][columnIndex];
    }

    public String getColumnName(int column) {
      return columns[column];
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

      data[rowIndex][columnIndex] = aValue;
    }

    public Class<?> getColumnClass(int columnIndex) {
      return data[0][columnIndex].getClass();
    }
  }

}
