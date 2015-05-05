package paoo.cappuccino.ihm.home;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ihm.util.cellrenderers.CompanyCellRenderer;
import paoo.cappuccino.ihm.util.cellrenderers.StateCellRenderer;
import paoo.cappuccino.ihm.util.disableablecombo.DisableableComboRenderer;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.util.DateUtils;
import paoo.cappuccino.util.ParticipationUtils;

/**
 * ViewController for the participation modification screen.
 */
public class HomeViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final HomeModel viewModel;
  private final MenuModel menu;
  private final IBusinessDayUcc dayUcc;
  private final ICompanyUcc companyUcc;

  private ErrorPanel viewError = new ErrorPanel();
  private JScrollPane scrollTable;
  private DefaultTableModel tableModel;
  private JComboDay dayList;

  /**
   * Creates a view controller for the participation modification screen.
   *
   * @param model The model of the view.
   * @param menu The model of the menu.
   * @param guiManager The app gui manager.
   * @param dayUcc The app business day use case controller.
   * @param companyUcc The app company use case controller.
   */
  public HomeViewController(HomeModel model, MenuModel menu, IGuiManager guiManager,
      IBusinessDayUcc dayUcc, ICompanyUcc companyUcc) {
    super(new BorderLayout());
    this.menu = menu;
    this.dayUcc = dayUcc;
    this.companyUcc = companyUcc;

    this.viewModel = model;

    List<IBusinessDayDto> listBusinessDays = dayUcc.getBusinessDays();

    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    dayList =
        new JComboDay(listBusinessDays.toArray(new IBusinessDayDto[listBusinessDays.size()]), menu);

    dayList.getCombo().addActionListener(
        e -> {
          IBusinessDayDto selectedDay = (IBusinessDayDto) dayList.getCombo().getSelectedItem();
          model.setSelectedDay(selectedDay);

          guiManager.getLogger().info(
              "[Home screen] selected day is "
                  + (selectedDay == null ? null : selectedDay.getEventDate()));
        });

    daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    String[] tableTitles = new String[] {"Nom entreprise", "État", "Annuler participation"};
    tableModel = new DefaultTableModel(tableTitles, 0) {

      private static final long serialVersionUID = 4116198217266394040L;

      @Override
      public boolean isCellEditable(int row, int column) {
        IBusinessDayDto day = model.getSelectedDay();

        if (day == null
            || DateUtils.getAcademicYear(day.getEventDate()) < DateUtils.getAcademicYear()) {
          return false;
        }

        if (column == 0) {
          return false;
        }

        IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);

        return !participation.isCancelled()
               && participation.getState() != State.DECLINED;
      }
    };

    JTable table = new JTable(tableModel);

    table.setRowHeight(35);

    // gestion affichage table
    TableColumn nameCol = table.getColumn(tableTitles[0]);
    nameCol.setMinWidth(nameCol.getWidth() * 6);
    nameCol.setCellRenderer(new CompanyCellRenderer());

    TableColumn stateCol = table.getColumn(tableTitles[1]);
    stateCol.setMinWidth(stateCol.getWidth() * 2);
    stateCol.setCellRenderer(new StateCellRenderer());
    ComboBoxModel<State> stateModel =
        new DefaultComboBoxModel<>(new State[] {State.INVITED, State.CONFIRMED, State.DECLINED,
            State.BILLED, State.PAID});
    JComboBox<State> stateCombo = new JComboBox<>(stateModel);
    stateCol.setCellEditor(new DefaultCellEditor(stateCombo));

    ListSelectionModel enabledStates = new DefaultListSelectionModel();
    stateCombo.setRenderer(new StateComboRenderer(enabledStates));
    stateCombo.addItemListener(e -> {
      if (e.getStateChange() != ItemEvent.SELECTED) {
        return;
      }

      int row = table.getEditingRow();
      if (row == -1) {
        return;
      }

      IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);
      State selectedState = (State) stateCombo.getSelectedItem();
      if (selectedState == participation.getState()) {
        return;
      }

      if (participation.isCancelled()) {
        JOptionPane.showMessageDialog(HomeViewController.this, "Participation annulée.",
            "Action invalide", JOptionPane.ERROR_MESSAGE);
        stateCombo.setSelectedItem(participation.getState());
        return;
      }

      if (!dayUcc.changeState(participation, selectedState)) {
        JOptionPane.showMessageDialog(HomeViewController.this,
            "Impossible de sélectionner cet état.", "Action invalide", JOptionPane.ERROR_MESSAGE);

        stateCombo.setSelectedItem(participation.getState());
      } else {
        guiManager.getLogger().info(
            "[Home screen] " + ((ICompanyDto) tableModel.getValueAt(row, 0)).getName() + " -> "
                + participation.getState());
      }

      table.repaint();
    });
    stateCombo.addPopupMenuListener(new PopupMenuListener() {
      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
        int row = table.getEditingRow();
        if (row == -1) {
          return;
        }

        IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);
        enabledStates.clearSelection();
        enabledStates.addSelectionInterval(participation.getState().ordinal(), participation
            .getState().ordinal());

        if (participation.isCancelled()) {
          return;
        }

        State[] states = ParticipationUtils.getFollowingStates(participation.getState());

        for (State state : states) {
          enabledStates.addSelectionInterval(state.ordinal(), state.ordinal());
        }
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {}

      @Override
      public void popupMenuCanceled(PopupMenuEvent event) {}
    });

    TableColumn cancelCol = table.getColumn(tableTitles[2]);
    cancelCol.setCellRenderer(new ButtonRenderer());

    JButton cancelButton = new JButton("Annuler");
    cancelCol.setCellEditor(new CancelButtonCellEditor(cancelButton));
    cancelButton.addActionListener(e -> {
      int row = table.getSelectedRow();

      IParticipationDto participation = (IParticipationDto) tableModel.getValueAt(row, 2);
      dayUcc.cancelParticipation(participation);
      guiManager.getLogger()
          .info(
              "[Home screen] " + ((ICompanyDto) tableModel.getValueAt(row, 0)).getName()
                  + " Cancelled");
    });

    this.scrollTable = new JScrollPane(table);
    scrollTable.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP, IhmConstants.M_GAP));
    this.add(this.scrollTable);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent clickEvent) {
        if (clickEvent.getClickCount() == 2) {
          ICompanyDto company =
              (ICompanyDto) table.getModel().getValueAt(table.getSelectedRow(), 0);

          menu.setCurrentPage(MenuEntry.COMPANY_DETAILS, company);
        }
      }
    });

    stateChanged(null);
  }

  /**
   * build data from accueilModel.
   */
  private void buildTable() {
    List<IParticipationDto> participations =
        dayUcc.getParticipations(viewModel.getSelectedDay().getId());

    tableModel.setRowCount(participations.size());
    for (int i = 0; i < participations.size(); i++) {
      IParticipationDto participation = participations.get(i);
      ICompanyDto company = companyUcc.getCompanyById(participation.getCompany());

      tableModel.setValueAt(company, i, 0);

      tableModel.setValueAt(participation.getState(), i, 1);

      tableModel.setValueAt(participation, i, 2);
    }
  }

  private void setError(int errno) {
    viewError.setError(errno);

    this.remove(scrollTable);
    this.add(viewError);

    this.repaint();
    this.revalidate();
  }

  private void clearError() {
    this.remove(viewError);
    this.add(scrollTable);

    this.repaint();
    this.revalidate();
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    if (viewModel.getSelectedDay() == null
        || !viewModel.getSelectedDay().equals(dayList.getCombo().getSelectedItem())) {
      dayList.getCombo().setSelectedItem(viewModel.getSelectedDay());
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

  private static class StateComboRenderer extends DisableableComboRenderer {

    private static final long serialVersionUID = -1013445198861342267L;

    public StateComboRenderer(ListSelectionModel enabled) {
      super(enabled);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      setText(LocalizationUtil.localizeState((State) value));

      return this;
    }
  }

  private static class ButtonRenderer extends JButton implements TableCellRenderer {

    private static final long serialVersionUID = -8966333763971995652L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean isFocus, int row, int col) {
      IParticipationDto participation = (IParticipationDto) value;

      setEnabled(participation.getState() != State.DECLINED
          && participation.getState() != State.INVITED && !participation.isCancelled());

      setText(participation.isCancelled() ? "Annulée" : "Annuler");
      return this;
    }
  }

  private class ErrorPanel extends JPanel {

    private static final long serialVersionUID = -4377590597739100439L;
    public static final int ERROR_NO_DAY_SELECTED = 1;
    public static final int ERROR_NO_PARTICIPATION = 2;

    private final JLabel errorMessage = new JLabelFont(null, 16);
    private final JPanel contents = new JPanel(new GridLayout(2, 1, 0, 10));


    private JButton createParticipationButton = new JButton("Inviter des entreprises");

    public ErrorPanel() {
      super(new GridBagLayout());

      createParticipationButton.addActionListener(e -> menu.setCurrentPage(
          MenuEntry.SELECT_COMPANY, viewModel.getSelectedDay()));

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

        case ERROR_NO_DAY_SELECTED:
          errorMessage.setText(IhmConstants.SELECT_A_DAY);
          break;

        case ERROR_NO_PARTICIPATION:
          errorMessage.setText(IhmConstants.ERROR_NO_COMPANY);
          contents.add(createParticipationButton);
          break;

        default:
      }
    }
  }
}
