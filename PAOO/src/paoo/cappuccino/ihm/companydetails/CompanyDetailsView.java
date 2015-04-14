package paoo.cappuccino.ihm.companydetails;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.IContactDto;

public class CompanyDetailsView extends JPanel{

  private CompanyDetailsModel model;
  private JTable table;

  public CompanyDetailsView(CompanyDetailsModel model) {

    super(new BorderLayout());
    this.model = model;
    table = new JTable();

    JPanel gridLayoutpanel = new JPanel(new GridLayout(3,1));
    JPanel panel = new JPanel();
    panel.add(new JLabel("Nom : " + model.getCompanyDto().getName()),FlowLayout.LEFT);
    panel.add(new JLabel("    "),FlowLayout.LEFT);
    // TODO ajouter getUserByid dans userUcc
    panel.add(new JLabel("Enregisteur : "),FlowLayout.LEFT);

    gridLayoutpanel.add(panel);

    panel = new JPanel();
    panel.add(new JLabel("Date de l'enregistrement : "
        +model.getCompanyDto().getRegisterDate().toString()),FlowLayout.LEFT);

    gridLayoutpanel.add(panel);

    panel = new JPanel(new GridLayout(1, 2));

    JPanel leftPanel = new JPanel(new BorderLayout());
    JPanel rightPanel = new JPanel(new BorderLayout());

    leftPanel.add(new JLabel("Adresse : "), BorderLayout.NORTH);

    JPanel leftInnerPanel = new JPanel(new GridLayout(3, 1));

    leftInnerPanel.add(new JLabel("Rue : " + model.getCompanyDto().getAddressStreet()));
    leftInnerPanel.add(new JLabel("Ville : " + model.getCompanyDto().getAddressTown()));
    leftInnerPanel.add(new JLabel("Boite : " + model.getCompanyDto().getAddressMailbox()));

    leftPanel.add(leftInnerPanel);

    rightPanel.add(new JLabel("Numero : "), BorderLayout.NORTH);
    rightPanel.add(new JLabel("Code postal : " + model.getCompanyDto().getAddressPostcode()));

    gridLayoutpanel.add(leftPanel);
    gridLayoutpanel.add(rightPanel);

    this.add(gridLayoutpanel,BorderLayout.NORTH);

    JPanel borderLayoutPanel = new JPanel(new BorderLayout());

    borderLayoutPanel.add(new JLabel("Personne de contact : "),BorderLayout.NORTH);

    if(model.getContactDto().size() == 0){
      borderLayoutPanel.add(new JLabel("Il n'y a aucune personne de contact pour cet entreprise."));
    }else{
     borderLayoutPanel.add(new JScrollPane(table));
      table.setModel(new tableModel(model.getContactDto()));
    }
    this.add(borderLayoutPanel);

    //TODO ajouter getBusinessDaybyCompanyId
  }

  public JTable getTable() {

    return table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom", "prenom", "Entreprise","Mail","Téléphone"};
    Object[][] data;

    public tableModel(List<IContactDto> contactDto) {

      data = new Object[contactDto.size()][];

      for (int i = 0; i < contactDto.size(); i++) {

        data[i] =
            new Object[]{contactDto.get(i).getLastName(), contactDto.get(i).getFirstName(),
            contactDto.get(i).getCompany(),contactDto.get(i).getEmail(),contactDto.get(i).getPhone()};
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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

      data[rowIndex][columnIndex] = aValue;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      return data[0][columnIndex].getClass();
    }
  }


}
