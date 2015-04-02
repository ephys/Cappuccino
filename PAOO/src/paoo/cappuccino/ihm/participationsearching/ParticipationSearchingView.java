package paoo.cappuccino.ihm.participationsearching;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ucc.ICompanyUcc;

@SuppressWarnings("serial")
public class ParticipationSearchingView extends JPanel implements ChangeListener {

  private ParticipationSearchingModel model;
  private ICompanyUcc companyUcc;
  private JTable table;
  private JScrollPane scrollPane;
  private boolean removedWidget;
  private JPanel centerPadding;

  public ParticipationSearchingView(ParticipationSearchingModel model, ICompanyUcc companyUcc) {

    setLayout(new BorderLayout());
    this.model = model;
    this.companyUcc = companyUcc;
    this.table = new JTable();
    this.scrollPane = new JScrollPane(table);
    this.add(scrollPane);
    this.model.addChangeListener(this);
  }


  @Override
  public void stateChanged(ChangeEvent event) {

    if (model.getParticipationDto() != null && model.getParticipationDto().length > 0) {

      if (this.removedWidget) {
        this.remove(this.centerPadding);
        this.add(this.scrollPane);
        this.removedWidget = false;
        this.revalidate();
        this.repaint();
      }

      this.table.setModel(new tableModel(model.getParticipationDto()));
      return;

    } else {

      if (!this.removedWidget) {
        this.centerPadding = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        centerPadding.add(new JLabel(errorMessage(model.getParticipationDto())));

        this.remove(this.scrollPane);
        this.add(centerPadding);
        this.removedWidget = true;
        this.revalidate();
        this.repaint();
      }

    }

  }


  public JTable getTable() {

    return this.table;
  }

  class tableModel extends AbstractTableModel {

    String[] columns = {"Nom entreprise", "Adresse entreprise", "Date de l'enregistrement", "Etat"};
    Object[][] data;

    public tableModel(IParticipationDto[] participationDto) {

      this.data = new Object[participationDto.length][];

      for (int i = 0; i < participationDto.length; i++) {

        ICompanyDto companyDto =
            ParticipationSearchingView.this.companyUcc.getCompanyById(participationDto[i]
                .getCompany());
        this.data[i] =
            new Object[] {companyDto.getName(), companyDto.getAddressTown(),
                companyDto.getRegisterDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy à hh:mm:ss")), participationDto[i].getState().toString(),
                companyDto.getId()};
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
  
  public String errorMessage(IParticipationDto[] participationDto){
    
      if(participationDto == null) return "Il n'y a aucune journée d'entreprise disponible.";
      else return "Il n'y a aucune participation correspondante.";
  }

}
