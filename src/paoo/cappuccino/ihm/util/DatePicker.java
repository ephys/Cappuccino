package paoo.cappuccino.ihm.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 * Widget used to pick a date in a list of dates.
 */
public class DatePicker extends JPanel {

  private final LocalDateTime minDate;
  private final LocalDateTime maxDate;
  private final Logger logger;

  private final JSpinner daysList = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
  private final JSpinner monthList = new JSpinner(new SpinnerListModel(Month.values()));
  private final JSpinner yearList;

  /**
   * Widget used to pick a date in a list of dates.
   *
   * @param minDate The date to which the selection cannot be lower. Null = no lower limit.
   * @param maxDate The date to which the selection cannot be higher. Null = no upper limit.
   */
  public DatePicker(LocalDateTime minDate, LocalDateTime maxDate, Logger logger) {
    super(new FlowLayout());

    this.minDate = minDate;
    this.maxDate = maxDate;
    this.logger = logger;

    yearList = new JSpinner(new SpinnerNumberModel(minDate == null
                                                   ? Integer.valueOf(LocalDateTime.now().getYear())
                                                   : Integer.valueOf(minDate.getYear()),
                                                   minDate == null ? null : minDate.getYear(),
                                                   maxDate == null ? null : maxDate.getYear(),
                                                   Integer.valueOf(1)));

    Border padding = BorderFactory.createEmptyBorder(5, 10, 5, 10);
    ((JSpinner.DefaultEditor) daysList.getEditor()).getTextField().setBorder(padding);
    ((JSpinner.DefaultEditor) monthList.getEditor()).getTextField().setBorder(padding);
    ((JSpinner.DefaultEditor) yearList.getEditor()).getTextField().setBorder(padding);

    daysList.setPreferredSize(new Dimension(100, 40));
    monthList.setPreferredSize(new Dimension(120, 40));
    yearList.setPreferredSize(new Dimension(100, 40));

    this.add(daysList);
    this.add(monthList);
    this.add(yearList);
  }

  /**
   * Retrieves the inputted day.
   *
   * @return The day as a localdatetime or null if it is out of range or invalid.
   */
  public LocalDateTime getSelection() {
    int day = (int) daysList.getValue();
    Month month = (Month) monthList.getValue();
    int year = (int) yearList.getValue();

    try {
      // TODO (if time allows): add JSpinners for hour/minute/second
      LocalDateTime selectedDate = LocalDateTime.of(year, month, day,
                                                    minDate.getHour(),
                                                    minDate.getMinute(),
                                                    minDate.getSecond());

      if (selectedDate.compareTo(minDate) < 0 || selectedDate.compareTo(maxDate) > 0) {
        return null;
      }

      return selectedDate;
    } catch (DateTimeException e) {
      logger.log(Level.FINE, "Failed to parse input date", e);
      return null;
    }
  }
}
