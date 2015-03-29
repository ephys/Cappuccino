package paoo.cappuccino.ihm.participationsearching;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class ParticipationSearchingModel extends BaseModel {
  private IParticipationDto[] participationDto;


  public void setCompanyDto(IParticipationDto[] participationDto) {

    this.participationDto = participationDto;

    dispatchChangeEvent();

  }

  public IParticipationDto[] getParticipationDto() {

    return this.participationDto;
  }


}
