package paoo.cappuccino.ihm.home;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the participation modification screen.
 */
public class HomeModel extends BaseModel {

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
}
