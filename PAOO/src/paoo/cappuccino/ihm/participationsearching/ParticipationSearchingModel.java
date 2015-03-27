package paoo.cappuccino.ihm.participationsearching;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class ParticipationSearchingModel extends BaseModel{
  private ICompanyDto[] companyDto;


  public void setCompanyDto(ICompanyDto[] companyDto) {

    this.companyDto = companyDto;

    dispatchChangeEvent();

  }

  public ICompanyDto[] getCompanyDto() {

    return this.companyDto;
  }


}
