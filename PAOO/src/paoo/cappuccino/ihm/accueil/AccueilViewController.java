package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.util.ParticipationUtils;

/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements
    ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;
  private TableModel dataModel = null;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu,
      IGuiManager guiManager) {
    super(new BorderLayout());
    this.model = model;
    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));// TODO listener
    JComboBox<IBusinessDayDto> dayList =
        new JComboBox<IBusinessDayDto>(model.getAllDays());
    dayList.addActionListener(e -> {
      int index = dayList.getSelectedIndex();
      if (index == -1) {
        model.setSelectedDay(null);
      }

      model.setSelectedDay(dayList.getItemAt(index));
    });

    daylistPanel.add(new JLabel("journée du : "));
    daylistPanel.add(dayList);

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


      public boolean isCellEditable(int row, int col) {
        return false;

      }
    };

    JTable table = new JTable(dataModel);
    table.setRowHeight(40);
    table.setDefaultRenderer(JComponent.class, new CellRenderer());
    JScrollPane scrollpane = new JScrollPane(table);
    this.add(scrollpane);
  }

  private class CellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row,
        int column) {
      // Si la valeur de la cellule est un JButton, on transtype cette valeur
      if (value instanceof JComponent)
        return (JComponent) value;
      else
        return this;
    }
  }

  protected Object[][] getTableCompanies() {
    IParticipationDto[] participations = model.getParticipations();
    Object[][] data = new Object[participations.length][2];
    for (int i = 0; i < participations.length; i++) {
      IParticipationDto currentParticipation = participations[i];
      // data[i][0] = currentCompany.getName();
      data[i][0] =
          new JLabelFont(model.getCompanyById(currentParticipation
              .getCompany()), 12);

      // constructionJ Jpanel
      JComboBox<IParticipationDto.State> states =
          new JComboBox<IParticipationDto.State>(
              ParticipationUtils.getFollowingStates(currentParticipation
                  .getState()));

      JButton cancel = new JButton("Annuler");
      cancel.addActionListener(e -> {
        // ucc evolve contact
        // modelTable.remove(this.row)
        });
      JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
      controls.add(states);
      controls.add(cancel);
      data[i][1] = controls;
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
