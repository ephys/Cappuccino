package paoo.cappuccino.ihm.companySelection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;

@SuppressWarnings("serial")
public class CompanySelectionView extends JPanel implements ChangeListener {

  CompanySelectionModel model;
  JTable table;
  String[] header = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement",
      "Selectionner"};
  DefaultTableModel tableModel;

  public CompanySelectionView(CompanySelectionModel model) {

    setLayout(new BorderLayout());
    this.model = model;
    this.tableModel = new DefaultTableModel(null, header);
    this.table = new JTable(tableModel);
    // waiting for mock dao...
    this.add(new JScrollPane(table));
    this.model.addChangeListener(this);
  }


  @Override
  public void stateChanged(ChangeEvent event) {

    ICompanyDto[] companyDto = model.getCompanyDto();
    boolean selectAll = model.getSelectAll();

    if (companyDto != null) {
      for (int i = 0; i < companyDto.length; i++) {

        tableModel.addRow(new Object[] {
            companyDto[i].getName(),
            companyDto[i].getAddressPostcode() + " " + companyDto[i].getAddressTown() + " "
                + companyDto[i].getAddressStreet() + companyDto[i].getAddressNum(),
            companyDto[i].getRegisterDate().toString(), selectAll});
      }

    } else {

      JPanel centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
      centerPadding.add(new JLabel("Il n'y a aucune journée d'entreprise pour l'instant."));

      this.add(centerPadding);
    }

  }

  public JTable getTable() {

    return this.table;
  }


}
