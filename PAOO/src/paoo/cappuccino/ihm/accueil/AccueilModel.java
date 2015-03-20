package paoo.cappuccino.ihm.accueil;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the Login View/ViewController.
 *
 * @author Opsomer Mathias
 */
public class AccueilModel extends BaseModel {

  private IBusinessDayDto selectedDay;

  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;

    dispatchChangeEvent();
  }

  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }
}
