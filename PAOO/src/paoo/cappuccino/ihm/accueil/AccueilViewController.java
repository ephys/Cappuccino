package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.util.ParticipationUtils;


/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel viewModel;
  private final MenuModel menu;
  private final IBusinessDayUcc dayUcc;
  private final ICompanyUcc companyUcc;

  private ErrorPanel viewError = new ErrorPanel();
  private JScrollPane tablePanel;
  private DefaultTableModel tableModel;
  private JComboBox<IBusinessDayDto> dayList;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu,
                               IGuiManager guiManager, IBusinessDayUcc dayUcc,
                               ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.menu = menu;
    this.dayUcc = dayUcc;
    this.companyUcc = companyUcc;
    guiManager.getLogger().info("AcceuilFrame");

    this.viewModel = model;

    IBusinessDayDto[] businessDays = dayUcc.getBusinessDays();
    if (businessDays.length == 0) {
      viewError.setError(ErrorPanel.ERROR_NO_DAY);
      this.add(viewError);

      return;
    }

    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    daylistPanel.add(new JLabelFont("journée du "));

    dayList = new JComboBox<>(businessDays);
    dayList.setRenderer(new DayRenderer());

    dayList.addActionListener(e -> {
      IBusinessDayDto selectedDay = (IBusinessDayDto) dayList.getSelectedItem();
      model.setSelectedDay(selectedDay);

      guiManager.getLogger().info("Home screen: selected day is "
                                  + (selectedDay == null ? null : selectedDay.getEventDate()));
    });

    daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    String[] tableTitles = new String[] {
        "Nom entreprise", "État", "Annuler participation"
    };
    tableModel = new DefaultTableModel(tableTitles, 0);

    JTable table = new JTable(tableModel);
    table.setRowHeight(35);

    // gestion affichage table
    TableColumn nameCol = table.getColumn(tableTitles[0]);
    nameCol.setMinWidth(nameCol.getWidth() * 6);
    nameCol.setCellRenderer(new CompanyRenderer());

    TableColumn stateCol = table.getColumn(tableTitles[1]);
    stateCol.setMinWidth(stateCol.getWidth() * 2);
    JComboBox<IParticipationDto.State> stateCombo = new JComboBox<>();
    stateCol.setCellEditor(new JComboEditor(stateCombo));
    stateCol.setCellRenderer((table1, value, isSelected, hasFocus, row, column)
                                 -> (JComboBox) value);
    stateCombo.addActionListener(e -> {
      int row = table.getSelectedRow();

      IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);
      JComboBox<State> stateList =
          (JComboBox<State>) tableModel.getValueAt(row, 1);

      dayUcc.changeState(participation, (State) stateList.getSelectedItem());
    });

    TableColumn cancelCol = table.getColumn(tableTitles[2]);
    cancelCol.setCellRenderer(new ButtonRenderer());

    JButton cancelButton = new JButton("Annuler");
    cancelCol.setCellEditor(new CancelButtonCellEditor(cancelButton));
    cancelButton.addActionListener(e -> {
      int row = table.getSelectedRow();

      IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);
      dayUcc.cancelParticipation(participation);
    });

    this.tablePanel = new JScrollPane(table);
    this.add(this.tablePanel);

    stateChanged(null);
  }

  /**
   * build data from accueilModel.
   */
  private void buildTable() {
    IParticipationDto[] participations = dayUcc.getParticipations(
        viewModel.getSelectedDay().getId());

    tableModel.setRowCount(participations.length);
    for (int i = 0; i < participations.length; i++) {
      IParticipationDto participation = participations[i];
      ICompanyDto company = companyUcc.getCompanyById(participation.getCompany());

      tableModel.setValueAt(company, i, 0);

      List<State> validStates = new ArrayList<>();
      validStates.add(participation.getState());
      Collections.addAll(validStates,
                         ParticipationUtils.getFollowingStates(participation.getState()));

      JComboBox<State> boxState =
          new JComboBox<>(validStates.toArray(new State[validStates.size()]));
      tableModel.setValueAt(boxState, i, 1);

      tableModel.setValueAt(participation, i, 2);
    }
  }

  private void setError(int errno) {
    viewError.setError(errno);

    this.remove(tablePanel);
    this.add(viewError);

    this.repaint();
    this.revalidate();
  }

  private void clearError() {
    this.remove(viewError);
    this.add(tablePanel);

    this.repaint();
    this.revalidate();
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    if (viewModel.getSelectedDay() != dayList.getSelectedItem()) {
      dayList.setSelectedItem(viewModel.getSelectedDay());
    }

    if (viewModel.getSelectedDay() == null) {
      setError(ErrorPanel.ERROR_NO_DAY_SELECTED);
    } else {
      buildTable();

      if (tableModel.getRowCount() == 0) {
        setError(ErrorPanel.ERROR_NO_PARTICIPATION);
      } else {
        clearError();
      }
    }
  }

  private static class ButtonRenderer extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean isFocus, int row, int col) {
      IParticipationDto participation = (IParticipationDto) value;

      setEnabled(participation.getState() != State.DECLINED
                 && participation.getState() != State.INVITED && !participation.isCancelled());

      setText(participation.isCancelled() ? "Annulé" : "Annuler");
      return this;
    }
  }

  private static class CompanyRenderer implements TableCellRenderer {

    private final JLabel label = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

      label.setText(((ICompanyDto) value).getName());

      return label;
    }
  }

  private static class DayRenderer extends BasicComboBoxRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);

      setText(value == null ? null : ((IBusinessDay) value).getEventDate().format(
          DateTimeFormatter.ofPattern("dd MMM yyyy")));

      return this;
    }
  }

  private class ErrorPanel extends JPanel {

    public static final int ERROR_NO_DAY = 0;
    public static final int ERROR_NO_DAY_SELECTED = 1;
    public static final int ERROR_NO_PARTICIPATION = 2;

    private final JLabel errorMessage = new JLabelFont(null, 20);
    private final JPanel contents = new JPanel(new GridLayout(2, 1, 0, 10));

    private JButton createDayButton = new JButton("Créer une journée");
    private JButton createParticipationButton = new JButton("Inviter des entreprises");

    public ErrorPanel() {
      super(new GridBagLayout());

      createDayButton.addActionListener(
          e -> menu.setCurrentPage(MenuEntry.CREATE_BDAY));
      createParticipationButton.addActionListener(
          e -> menu.setCurrentPage(MenuEntry.SELECT_COMPANY));

      this.add(contents, new GridBagConstraints());
      contents.add(errorMessage);
    }

    private void clear() {
      contents.removeAll();
      contents.add(errorMessage);
    }

    public void setError(int errno) {
      clear();

      switch (errno) {
        case ERROR_NO_DAY:
          errorMessage.setText("Aucune journée disponible.");
          contents.add(createDayButton);
          break;

        case ERROR_NO_DAY_SELECTED:
          errorMessage.setText("Sélectionnez une journée.");
          contents.add(createDayButton);
          break;

        case ERROR_NO_PARTICIPATION:
          errorMessage.setText("Aucune entreprise invitée.");
          contents.add(createParticipationButton);
          break;

        default:
      }
    }
  }
}
