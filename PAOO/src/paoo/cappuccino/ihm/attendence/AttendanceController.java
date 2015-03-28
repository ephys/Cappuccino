package paoo.cappuccino.ihm.attendence;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
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
    this.setBorder(new EmptyBorder(IhmConstants.M_GAP, 0,
        IhmConstants.M_GAP, 0));
    // Log
    manager.getLogger().info("Attendance Frame");

    // top
    JPanel top = new JPanel(new GridLayout(1, 0));
    JComboDay daysList = new JComboDay(businessDayUcc.getBusinessDays());// TODO verifier argument
    daysList.getCombo().addActionListener(e -> {

    });

    JPanel comboCompanyPanel =
        new JPanel(new FlowLayout(FlowLayout.CENTER));
    comboCompanyPanel.add(new JLabel("Entreprise"));
    JComboBox<ICompanyDto> companiesList =
        new JComboBox<ICompanyDto>(companyUcc.getAllCompanies()); // TODO
    companiesList.addActionListener(e -> {

    });

    companiesList.setRenderer(new CompanyRenderer());
    comboCompanyPanel.add(companiesList);

    top.add(daysList);
    top.add(comboCompanyPanel);

    this.add(top, BorderLayout.NORTH);


    // center
    JPanel center = new JPanel(new BorderLayout());

    // tous

    JCheckBox allSelect = new JCheckBox();
    JPanel allSelectPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    allSelectPanel.add(allSelect);
    allSelectPanel.add(new JLabel("Tous"));
    center.add(allSelectPanel, BorderLayout.NORTH);

    // table
    String[] titles = {"nom", "selec"};
    Object[][] data = { {"albert", true}, {"jean", false}};// TODO change it
    TableContactModel modelTable = new TableContactModel(titles, data);
    JTable table = new JTable(modelTable);
    table.getColumn("selec").setCellEditor(
        new DefaultCellEditor(new JCheckBox()));

    center.add(new JScrollPane(table));


    this.add(center);
    JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton validate = new JButton("Valider");
    validate.addActionListener(e -> {
      // TODO action valider
      });
    controls.add(validate);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
   */
  @Override
  public void stateChanged(ChangeEvent arg0) {
    // TODO

  }
}
