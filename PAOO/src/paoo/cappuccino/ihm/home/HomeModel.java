package paoo.cappuccino.ihm.home;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class HomeModel extends BaseModel {

  private IBusinessDayDto selectedDay;

  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;

    dispatchChangeEvent();
  }

  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }
}
