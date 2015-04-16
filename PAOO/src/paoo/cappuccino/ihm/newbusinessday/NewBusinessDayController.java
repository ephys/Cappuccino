package paoo.cappuccino.ihm.newbusinessday;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.IhmConstants;
import paoo.cappuccino.ucc.IBusinessDayUcc;

/**
 * ViewController for the new BusinessDay Gui.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewBusinessDayController extends JPanel {

  /**
   * Creates a new ViewController for the new company gui.
   *
   * @param model The ViewController's model.
   * @param manager The manager responsible for the opening/closing this frame.
   * @param businessDayUcc
   */
  public NewBusinessDayController(NewBusinessDayModel model, MenuModel menu, IGuiManager manager,
      IBusinessDayUcc businessDayUcc) {
    super(new BorderLayout());
    this.setBorder(new EmptyBorder(IhmConstants.L_GAP, IhmConstants.M_GAP, 0, IhmConstants.M_GAP));

    // log
    manager.getLogger().info("NewBusinessDay Frame");

    GregorianCalendar calendar = new GregorianCalendar();
    Date initDate = calendar.getTime();
    calendar.add(Calendar.YEAR, 200);
    Date latestDate = calendar.getTime();
    SpinnerDateModel modelSpinner =
        new SpinnerDateModel(initDate, initDate, latestDate, Calendar.YEAR);
    JSpinner spinnerDate = new JSpinner(modelSpinner);
    spinnerDate.setEditor(new JSpinner.DateEditor(spinnerDate, "hh:mm  dd/MM/yyyy"));


    JPanel controls =
        new JPanel(new FlowLayout(FlowLayout.RIGHT, IhmConstants.M_GAP, IhmConstants.M_GAP));


    JButton createButton = new JButton("Créer");
    createButton.addActionListener(e -> {
      Date input = (Date) spinnerDate.getValue();
      LocalDateTime date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      try {
        IBusinessDayDto createdDate = businessDayUcc.create(date);
        manager.getLogger().info("new BusinessDay created : " + date.toString());
        JOptionPane.showMessageDialog(null, "Journée crée");
        menu.setCurrentPage(MenuEntry.SELECT_COMPANY, createdDate);
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
      }
    });


    controls.add(createButton);

    this.add(controls, BorderLayout.SOUTH);
    // end buttons //

    this.add(new NewBusinessDayView(model, spinnerDate));
  }

  private static class ComboEntrepriseRenderer implements ListCellRenderer<ICompanyDto> {

    @Override
    public Component getListCellRendererComponent(JList<? extends ICompanyDto> arg0,
        ICompanyDto value, int arg2, boolean arg3, boolean arg4) {
      if (value == null)
        return new JLabel();
      return new JLabel(value.getName());
    }
  }
}
