package paoo.cappuccino.ihm.attendence;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.CompanyRenderer;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.ucc.IContactUcc;

/**
 * ViewController for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class AttendanceController extends JPanel implements ChangeListener {
  private final TableContactModel modelTable = new TableContactModel();
  private final AttendanceModel model;
  private final IContactUcc contactUcc;
  private final JCheckBox allSelect = new JCheckBox();
  private final JTable table = new JTable(modelTable);

  /**
   * Creates a new ViewController for the new attendance gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param companyUcc
   * @param businessDayUcc
   * @param contactUcc
   */
  public AttendanceController(AttendanceModel model, MenuModel menu,
      IGuiManager manager, ICompanyUcc companyUcc,
      IBusinessDayUcc businessDayUcc, IContactUcc contactUcc) {
    super(new BorderLayout());
    this.model = model;
    this.contactUcc = contactUcc;
    this.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP, IhmConstants.M_GAP));
    // Log
    manager.getLogger().info("Attendance Frame");

    // listener
    model.addChangeListener(this);
    // top
    JPanel top = new JPanel(new GridLayout(1, 0));
    List<IBusinessDayDto> listDay = businessDayUcc.getBusinessDays();

    if (listDay.size() == 0) {

      JPanel noDay = new JPanel();
      noDay.add(new JLabel("aucune journée des entreprises"));
      top.add(noDay);

    } else {

      JComboDay daysList = new JComboDay(listDay.toArray(new IBusinessDayDto[listDay.size()]));
      model.setSelectedDay((IBusinessDayDto) daysList.getCombo()
          .getSelectedItem());
      daysList.getCombo().addActionListener(
          e -> model.setSelectedDay((IBusinessDayDto) daysList.getCombo().getSelectedItem()));
      top.add(daysList);

    }

    List<ICompanyDto> companyList = companyUcc.getAllCompanies();// TODO chose them

    if (companyList.size() == 0) {

      JPanel noCompany = new JPanel();
      noCompany.add(new JLabel(
          "Aucune entreprise inscrite pour cette journée"));
      top.add(noCompany);

    } else {

      JPanel comboCompanyPanel =
          new JPanel(new FlowLayout(FlowLayout.CENTER));
      comboCompanyPanel.add(new JLabel("Entreprise"));
      List<ICompanyDto> allCompanies = companyUcc.getAllCompanies();
      JComboBox<ICompanyDto> comboCompanies =
          new JComboBox<>(allCompanies.toArray(new ICompanyDto[allCompanies.size()]));
      model.setSelectedCompany((ICompanyDto) comboCompanies
          .getSelectedItem());
      comboCompanies.addActionListener(e -> model.setSelectedCompany((ICompanyDto) comboCompanies
          .getSelectedItem()));
      comboCompanies.setRenderer(new CompanyRenderer());
      comboCompanyPanel.add(comboCompanies);
      top.add(comboCompanyPanel);

    }
    this.add(top, BorderLayout.NORTH);


    // center
    JPanel center = new JPanel(new BorderLayout());



    // table

    TableColumn columnCheckBox =
        table.getColumn(table.getColumnName(table.getColumnCount() - 1));
    columnCheckBox.setCellEditor(new CheckBoxCellEditor(new JCheckBox(),
        model, table));
    columnCheckBox.setMaxWidth(columnCheckBox.getMaxWidth() / 6);
    center.add(new JScrollPane(table));


    this.add(center);
    JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton validate = new JButton("Valider");
    validate.addActionListener(e -> {
      // TODO action valider
      });
    controls.add(validate);

    // tous

    allSelect.addActionListener(e -> {
      if (model.getAllSelected() != allSelect.isSelected()) {
        model.setAllSelected(allSelect.isSelected());
      }
    });
    JPanel allSelectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    allSelectPanel.add(allSelect);
    allSelectPanel.add(new JLabel("Tous"));
    center.add(allSelectPanel, BorderLayout.NORTH);

    this.add(controls, BorderLayout.SOUTH);
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent arg0) {
    System.out.println("state changed");
    // check tous TODO
    if (model.getAllSelected()) {
      for (int i = 0; i < table.getRowCount(); i++) {
        modelTable.setValueAt(true, i, table.getColumnCount() - 1);
      }
    }

    if (allSelect.isSelected() != model.getAllSelected()) {
      allSelect.setSelected(model.getAllSelected());
    }


    // change companies TODO

    // change contacts
    if (model.getSelectedCompany() != null) {
      modelTable.changeData(contactUcc.getContactByCompany(model.getSelectedCompany().getId()));
    }
    table.repaint();
  }
}
