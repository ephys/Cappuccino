package paoo.cappuccino.ihm.home;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the participation modification screen.
 */
public class HomeModel extends BaseModel implements Initializable {

  private IBusinessDayDto selectedDay;

  /**
   * Sets which day is selected in the list of business days.
   * @param selectedDay The selected day. Nullable.
   */
  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;

    dispatchChangeEvent();
  }

  /**
   * Returns the day selected in the list of business days. Nullable.
   */
  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }

  @Override
  public void init(Object[] data) {
    if (data == null || data.length == 0) {
      return;
    }

    if (!(data[0] instanceof IBusinessDayDto)) {
      throw new IllegalArgumentException("Incorrect initialization data for CompanySelectionModel");
    }

    setSelectedDay((IBusinessDayDto) data[0]);
  }
}
