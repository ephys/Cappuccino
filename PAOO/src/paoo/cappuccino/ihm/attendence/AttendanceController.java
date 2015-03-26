package paoo.cappuccino.ihm.attendence;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.CompanyRenderer;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ihm.util.JComboDay;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * ViewController for the registration Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class AttendanceController extends JPanel {

  /**
   * Creates a new ViewController for the new attendance gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param companyUcc
   * @param businessDayUcc
   */
  public AttendanceController(AttendanceModel model, MenuModel menu, IGuiManager manager,
      ICompanyUcc companyUcc, IBusinessDayUcc businessDayUcc) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, IhmConstants.M_GAP,
        IhmConstants.M_GAP));
    // Log
    manager.getLogger().info("Attendance Frame");

    // top
    JPanel top = new JPanel(new GridLayout(1, 0));
    JComboDay daysList = new JComboDay(businessDayUcc.getBusinessDays());// TODO verifier argument
    daysList.getCombo().addActionListener(e -> {

    });

    JPanel comboCompanyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    comboCompanyPanel.add(new JLabel("Entreprise"));
    JComboBox<ICompanyDto> companiesList = new JComboBox<ICompanyDto>(companyUcc.getAllCompanies()); // TODO
    companiesList.addActionListener(e -> {

    });

    companiesList.setRenderer(new CompanyRenderer());
    comboCompanyPanel.add(companiesList);

    top.add(daysList);
    top.add(comboCompanyPanel);

    this.add(top, BorderLayout.NORTH);


    // center



    JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton validate = new JButton("Valider");
    validate.addActionListener(e -> {
      // TODO action valider
      });
    controls.add(validate);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new AttendanceView(model));
  }
}
