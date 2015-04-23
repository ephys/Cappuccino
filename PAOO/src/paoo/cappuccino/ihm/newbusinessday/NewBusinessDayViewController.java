package paoo.cappuccino.ihm.newbusinessday;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.menu.MenuEntry;
import paoo.cappuccino.ihm.menu.MenuModel;
import paoo.cappuccino.ihm.util.DatePicker;
import paoo.cappuccino.ihm.util.JLabelFont;
import paoo.cappuccino.ihm.util.LocalizationUtil;
import paoo.cappuccino.ucc.IBusinessDayUcc;

/**
 * ViewController for the screen used to create a new business day.
 *
 * @author Opsomer Mathias
 */
@SuppressWarnings("serial")
public class NewBusinessDayViewController extends JPanel {

  /**
   * Creates a new ViewController for the screen used to create a new business day.
   */
  public NewBusinessDayViewController(MenuModel menu, IGuiManager manager,
      IBusinessDayUcc businessDayUcc) {
    super(new GridBagLayout());
    JLabel errorDay = new JLabel();
    errorDay.setForeground(Color.red);

    final DatePicker datePicker =
        new DatePicker(LocalDateTime.now(), LocalDateTime.now().plusYears(
            20), manager.getLogger());

    JButton createButton = new JButton("Créer");
    createButton
        .addActionListener(e -> {
          LocalDateTime selectedDate = datePicker.getSelection();

          if (selectedDate == null) {

            errorDay.setText("La date entrée n'est pas valide.");
            return;
          }
          try {
            IBusinessDayDto createdDate =
                businessDayUcc.create(selectedDate);
            manager.getLogger().info(
                "Created a new business day : "
                    + LocalizationUtil.localizeDate(selectedDate));
            menu.setCurrentPage(MenuEntry.SELECT_COMPANY, createdDate);
          } catch (IllegalArgumentException event) {
            errorDay
                .setText("Une journée des entreprise existe déjà pour cette année.");
          }

        });

    JPanel mainPanel = new JPanel(new GridLayout(3, 1));
    JLabel titleLabel =
        new JLabelFont("Date de la nouvelle journée :", 20);
    titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    mainPanel.add(titleLabel);

    JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
    controls.add(datePicker);
    controls.add(createButton);

    mainPanel.add(controls);
    mainPanel.add(errorDay);

    this.add(mainPanel, new GridBagConstraints());
  }
}
