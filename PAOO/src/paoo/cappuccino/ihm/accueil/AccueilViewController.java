package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ParticipationUtils;


/**
 * ViewController for the Login Gui.
 *
 * @author Opsomer Mathias
 */
public class AccueilViewController extends JPanel implements ChangeListener {

  private static final long serialVersionUID = 3071496812344175953L;
  private final AccueilModel model;
  private JTable table;
  private DefaultTableModel tableModel;
  private String[] titles;

  private JComboBox<IBusinessDayDto> dayList;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu, IGuiManager guiManager,
      IBusinessDayUcc dayUcc) {
    super(new BorderLayout());
    guiManager.getLogger().info("AcceuilFrame");

    this.model = model;
    IBusinessDayDto[] businessDays = dayUcc.getBusinessDays();
    if (businessDays.length == 0) {
      JPanel errorPanel = new JPanel(new GridLayout(2, 1, 10, 10));

      JLabel errorMessage = new JLabelFont("Aucune journée disponible.", 20);
      errorMessage.setHorizontalAlignment(JLabel.CENTER);
      errorPanel.add(errorMessage);

      JButton redirectionButton = new JButton("Créer une journée");
      redirectionButton.addActionListener(e -> {
        menu.setCurrentPage(MenuEntry.CREATE_BDAY);
      });
      errorPanel.add(redirectionButton);

      JPanel errorWrapper = new JPanel(new GridBagLayout());
      errorWrapper.add(errorPanel, new GridBagConstraints());
      this.add(errorWrapper);
      return;
    }

    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    daylistPanel.add(new JLabelFont("journée du "));

    dayList = new JComboBox<>(businessDays);
    dayList.setRenderer(new dayRenderer());
    dayList.addActionListener(e -> {
      IBusinessDayDto selectedDay = (IBusinessDayDto) dayList.getSelectedItem();
      model.setSelectedDay(selectedDay);


      guiManager.getLogger().info(
          "Home screen: selected day is "
              + (selectedDay == null ? null : selectedDay.getEventDate()));
    });
    dayList.setSelectedIndex(0);

    daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    titles = new String[] {"Nom entreprise", "État", "Annuler participation"};
    tableModel = new DefaultTableModel(titles, 0);

    table = new JTable(tableModel);
    TableColumn nameCol = table.getColumn(titles[0]);
    TableColumn stateCol = table.getColumn(titles[1]);
    TableColumn cancelCol = table.getColumn(titles[2]);

    cancelCol.setCellRenderer(new ButtonRenderer());
    cancelCol.setCellEditor(new ButtonEditor(new JCheckBox()));
    stateCol.setCellRenderer(new ComboRenderer());
    stateCol.setCellEditor(new JComboEditor(new JComboBox<IParticipationDto.State>()));

    // gestion affichage table
    nameCol.setMinWidth(nameCol.getWidth() * 6);
    stateCol.setMinWidth(stateCol.getWidth() * 2);
    table.setRowHeight(35);

    this.add(new JScrollPane(table));

    stateChanged(null);
  }

  /**
   * build data from accueilModel
   */
  private Object[][] buildData() {
    if (model.getSelectedDay() == null) {
      return new Object[0][0];
    }

    IParticipationDto[] participations = new IParticipationDto[0];
    // TODO: fetch from bday ucc (getAttendingCompanies, returns an array of participation <- TOFIX)

    Object[][] data = new Object[model.getParticipations().keySet().size()][3];
    for (int i = 0; i < participations.length; i++) {
      ICompanyDto company = null; // TODO: companyUcc.getCompanyById(participation[i].getId());
      data[i][0] = company;
      List<State> states = new ArrayList<State>();
      states.add(model.getParticipations().get(company).getState());

      // possibleStates
      State[] followingStates =
          ParticipationUtils.getFollowingStates(model.getParticipations().get(company).getState());
      for (int j = 0; j < followingStates.length; j++)
        states.add(followingStates[i]);

      JComboBox<State> possibleState =
          new JComboBox<IParticipationDto.State>((State[]) states.toArray());
      data[i][1] = possibleState;

      data[i][2] = "Annuler";
      i++;
    }
    return data;
  }

  /**
   *
   * @return
   */
  private HashMap<String, IParticipationDto> getParticipations() {
    HashMap<String, IParticipationDto> map = new HashMap<String, IParticipationDto>();
    List<IParticipationDto> participations = new ArrayList<IParticipationDto>();
    // TODO participations = IParticipationUcc.getParticipationFor(model.getSelectedDay());
    for (IParticipationDto iParticipationDto : participations) {
      // TODO map.put(EntrepriseUcc.getById(iParticipationDto.getCompany()), iParticipationDto)
    }
    return map;
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    if (model.getSelectedDay() == dayList.getSelectedItem()) {
      return;
    }

    dayList.setSelectedItem(model.getSelectedDay());
    tableModel.setDataVector(buildData(), titles);
  }

  class ButtonRenderer extends JButton implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean isFocus, int row, int col) {
      setText((value != null) ? value.toString() : "");
      return this;
    }
  }
  class ComboRenderer extends JComboBox<State> implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean isFocus, int row, int col) {
      return (JComboBox<State>) value;
    }
  }
  class dayRenderer implements ListCellRenderer<IBusinessDayDto> {

    @Override
    public Component getListCellRendererComponent(JList<? extends IBusinessDayDto> list,
        IBusinessDayDto value, int index, boolean isSelected, boolean cellHasFocus) {
      if (value == null)
        return new JLabel();
      return new JLabel(value.getEventDate().toString());
    }

  }
}
