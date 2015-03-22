package paoo.cappuccino.ihm.accueil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellRenderer;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ucc.IBusinessDayUcc;
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
  private JTable table;

  /**
   * Creates a new ViewController for the Login gui.
   *
   * @param model The ViewController's model.
   */
  public AccueilViewController(AccueilModel model, MenuModel menu,
      IGuiManager guiManager, IBusinessDayUcc dayUcc) {
    super(new BorderLayout());
    guiManager.getLogger().info("AcceuilFrame");

    this.model = model;
    model.addChangeListener(this);

    // north
    JPanel daylistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JComboBox<IBusinessDayDto> dayList =
        new JComboBox<>(dayUcc.getBusinessDays());
    dayList.addActionListener(e -> {
      model.setSelectedDay((IBusinessDayDto) dayList.getSelectedItem());
      guiManager.getLogger().info(
          "Accueil changement journée : "
              + model.getSelectedDay().getEventDate().toString());

    });
    daylistPanel.add(new JLabelFont("journée du : "));
    daylistPanel.add(dayList);

    this.add(daylistPanel, BorderLayout.NORTH);

    // center
    String[] titles =
        new String[] {"Nom entreprise", "État", "Annuler participation"};
    model.setParticipations(getParticipations());
    table = new JTable(new TableCompaniesModel(buildMockData(), titles));
    table.getColumn("Annuler participation").setCellRenderer(
        new ButtonRenderer());
    table.getColumn("Annuler participation").setCellEditor(
        new ButtonEditor(new JCheckBox()));
    table.getColumn("État").setCellRenderer(new ComboRenderer());
    table.getColumn("État").setCellEditor(
        new JComboEditor(new JComboBox<IParticipationDto.State>()));


    // gestion affichage table
    table.getColumn("Nom entreprise").setMinWidth(
        table.getColumn("Nom entreprise").getWidth() * 6);
    table.getColumn("État").setMinWidth(
        table.getColumn("État").getWidth() * 2);
    table.setRowHeight(35);
    this.add(new JScrollPane(table));

  }

  /**
   * build data from accueilModel
   */
  private Object[][] buildData() {
    Object[][] data =
        new Object[model.getParticipations().keySet().size()][3];
    int i = 0;
    for (String company : model.getParticipations().keySet()) {
      data[i][0] = company;
      ArrayList<State> states = new ArrayList<State>();
      states.add(model.getParticipations().get(company).getState());

      // possibleStates
      State[] followingStates =
          ParticipationUtils.getFollowingStates(model.getParticipations()
              .get(company).getState());
      for (int j = 0; j < followingStates.length; j++)
        states.add(followingStates[i]);

      JComboBox<State> possibleState =
          new JComboBox<IParticipationDto.State>(
              (State[]) states.toArray());
      data[i][1] = possibleState;

      data[i][2] = "Annuler";
      i++;
    }
    return data;
  }

  private Object[][] buildMockData() {
    Object[] companies = {"asbl", "sa", "me"};
    Object[][] data = new Object[companies.length][3];
    for (int i = 0; i < companies.length; i++) {
      data[i][0] = companies[i];

      JComboBox<State> possibleState =
          new JComboBox<State>(new State[] {State.BILLED, State.CANCELLED,
              State.DECLINED});
      data[i][1] = possibleState;
      data[i][2] = "annuler";
    }
    return data;
  }

  /**
   *
   * @return
   */
  private HashMap<String, IParticipationDto> getParticipations() {
    HashMap<String, IParticipationDto> map =
        new HashMap<String, IParticipationDto>();
    List<IParticipationDto> participations =
        new ArrayList<IParticipationDto>();
    // TODO participations = IParticipationUcc.getParticipationFor(model.getSelectedDay());
    for (IParticipationDto iParticipationDto : participations) {
      // TODO map.put(EntrepriseUcc.getById(iParticipationDto.getCompany()), iParticipationDto)
    }
    return map;
  }

  @Override
  public void stateChanged(ChangeEvent event) {}

  class ButtonRenderer extends JButton implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean isFocus, int row, int col) {
      setText((value != null) ? value.toString() : "");
      return this;
    }
  }
  class ComboRenderer extends JComboBox<State> implements
      TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean isFocus, int row, int col) {
      return (JComboBox<State>) value;
    }
  }
}
