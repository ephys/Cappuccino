package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;
  private TableModel dataModel = null;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu, IGuiManager guiManager) {
    super(new BorderLayout());
    this.model = model;
    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    // JComboBox<IBusinessDayDto> dayList = new JComboBox<>(model.getAllDays());
    // dayList.addActionListener(e -> {
    // int index = dayList.getSelectedIndex();
    // if (index == -1) {
    // model.setSelectedDay(null);
    // }
    //
    // model.setSelectedDay(dayList.getItemAt(index));
    // });

    daylistPanel.add(new JLabel("journée du : "));
    // daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    dataModel = new AbstractTableModel() {
      private Object[][] data = getTableCompanies();
      private String[] title = {"Nom entreprise", " État"};

      public int getColumnCount() {
        return title.length;
      }

      public int getRowCount() {
        return data.length;
      }

      public Object getValueAt(int row, int col) {
        return data[row][col];
      }

      public String getColumnName(int col) {
        return this.title[col];
      }
    //Retourne vrai si la cellule est éditable : celle-ci sera donc éditable
      public boolean isCellEditable(int row, int col){
        //On appelle la méthode getValueAt qui retourne la valeur d'une cellule
        //Et on effectue un traitement spécifique si c'est un JButton
        if(getValueAt(0, col) instanceof JButton)
          return false;
        return true;
      }
    };
    JTable table = new JTable(dataModel);
    table.setDefaultRenderer(JButton.class, new CellRenderer());
    JScrollPane scrollpane = new JScrollPane(table);
    this.add(scrollpane);
  }

  private class CellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      // Si la valeur de la cellule est un JButton, on transtype cette valeur
      if (value instanceof JButton)
        return (JButton) value;
      else
        return this;
    }
  }

  protected Object[][] getTableCompanies() {
    // ICompanyDto[] companies = model.getCompanies();
    String[] companies = {"test", "test", "test", "teste"};
    Object[][] data = new Object[companies.length][2];
    for (int i = 0; i < companies.length; i++) {
      String currentCompany = companies[i];
      // data[i][0] = currentCompany.getName();
      data[i][0] = currentCompany;

      // constructionJ Jpanel
      // JComboBox<IParticipationDto.State> states =
      // new JComboBox<IParticipationDto.State>(model.getPossibleState(currentCompany));

      JComboBox<IParticipationDto.State> states = new JComboBox<IParticipationDto.State>();
      JButton cancel = new JButton("Annuler");
      cancel.addActionListener(e -> {
        // ucc evolve contact
        // modelTable.remove(this.row)
        });
      JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
      controls.add(states);
      controls.add(cancel);
      data[i][1] = cancel;
    }
    return data;
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent event) {



  }
}
