package paoo.cappuccino.ihm.participationsearching;

import java.util.List;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class ParticipationSearchingModel extends BaseModel {
  private List<IParticipationDto> participationDto;


  public void setParticipationDto(List<IParticipationDto> participationDto) {

    this.participationDto = participationDto;

    dispatchChangeEvent();

  }

  public List<IParticipationDto> getParticipationDto() {

    return this.participationDto;
  }


}
