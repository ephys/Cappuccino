package paoo.cappuccino.ihm.accueil;

import java.util.HashMap;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the Login View/ViewController.
 *
 * @author Opsomer Mathias
 */
public class AccueilModel extends BaseModel {

  private IBusinessDayDto selectedDay;
  private HashMap<String, IParticipationDto> participations;

  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;

    dispatchChangeEvent();
  }

  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }

  /**
   * @return the participations
   */
  public HashMap<String, IParticipationDto> getParticipations() {
    return participations;
  }

  /**
   * @param participations the participations to set
   */
  public void setParticipations(HashMap<String, IParticipationDto> participations) {
    this.participations = participations;
  }
}
