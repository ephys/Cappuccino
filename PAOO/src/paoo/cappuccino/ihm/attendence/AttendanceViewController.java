package paoo.cappuccino.ihm.attendence;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
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
import paoo.cappuccino.ihm.util.CompanyListRenderer;
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
public class AttendanceViewController extends JPanel implements ChangeListener {
  private final TableContactModel modelTable = new TableContactModel();
  private final AttendanceModel model;
  private final IContactUcc contactUcc;
  private final JTable table = new JTable(modelTable);
  private JComboBox<ICompanyDto> comboCompanies;
  private final JPanel comboCompanyPanel;
  private final ICompanyUcc companyUcc;
  private JComboDay comboDay;

  /**
   * Creates a new ViewController for the new attendance gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param companyUcc
   * @param businessDayUcc
   * @param contactUcc
   */
  public AttendanceViewController(AttendanceModel model, MenuModel menu, IGuiManager manager,
      ICompanyUcc companyUcc, IBusinessDayUcc businessDayUcc, IContactUcc contactUcc) {
    super(new BorderLayout());
    this.model = model;
    // initialisation ucc
    this.contactUcc = contactUcc;
    this.companyUcc = companyUcc;

    // initialisation variables
    JCheckBox allSelect = new JCheckBox("Tout cocher");
    comboCompanies = new JComboBox<ICompanyDto>();

    this.setBorder(new EmptyBorder(IhmConstants.M_GAP, IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP));
    // Log
    manager.getLogger().info("Attendance Frame");

    // listener
    model.addChangeListener(this);
    // top
    JPanel top = new JPanel(new GridLayout(1, 0));

    comboCompanyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    comboCompanies.addActionListener(e -> model.setSelectedCompany((ICompanyDto) comboCompanies
        .getSelectedItem()));
    comboCompanies.setRenderer(new CompanyListRenderer());

    List<IBusinessDayDto> listDay = businessDayUcc.getBusinessDays();

    if (listDay.size() == 0) {

      JPanel noDay = new JPanel();
      noDay.add(new JLabel("aucune journée des entreprises"));
      top.add(noDay);

    } else {

      comboDay = new JComboDay(listDay.toArray(new IBusinessDayDto[listDay.size()]));
      model.setSelectedDay((IBusinessDayDto) comboDay.getCombo().getSelectedItem());
      comboDay.getCombo().addActionListener(
          e -> model.setSelectedDay((IBusinessDayDto) comboDay.getCombo().getSelectedItem()));
      top.add(comboDay);

    }
    top.add(comboCompanyPanel);

    this.add(top, BorderLayout.NORTH);


    // center
    JPanel center = new JPanel(new BorderLayout());



    // table

    TableColumn columnCheckBox = table.getColumn(table.getColumnName(table.getColumnCount() - 1));
    columnCheckBox.setCellEditor(new CheckBoxCellEditor(new JCheckBox(), model, table, allSelect));
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

    allSelect.addItemListener(e -> {

      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.model.setSelectAll(true);
      } else {
        this.model.setSelectAll(false);
      }

      if (this.model.isNotDeselectAll()) {
        this.model.setNotDeselectAll(false);
      }
    });

    JPanel allSelectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    allSelectPanel.add(allSelect);
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
    boolean selectAll = model.isSelectAll();

    for (int i = 0; i < modelTable.getRowCount(); i++) {

      table.getModel().setValueAt(selectAll, i, table.getColumnCount() - 1);

    }

    System.out.println("changement entreprises");
    List<ICompanyDto> companyList = companyUcc.getAllCompanies();// TODO chose them
    comboCompanyPanel.removeAll();
    if (companyList.size() == 0) {
      comboCompanyPanel.add(new JLabel(IhmConstants.ERROR_NO_COMPANIES));
    } else {
      comboCompanies.removeAllItems();
      for (ICompanyDto iCompanyDto : companyList) {
        comboCompanies.addItem(iCompanyDto);
      }
      comboCompanyPanel.add(new JLabel("Entreprise"));
      comboCompanyPanel.add(comboCompanies);
    }
    comboCompanyPanel.repaint();

    System.out.println("changement contacts");
    if (model.getSelectedCompany() != null) {
      modelTable.changeData(contactUcc.getContactByCompany(model.getSelectedCompany().getId()));
    }

    table.repaint();
  }
}
