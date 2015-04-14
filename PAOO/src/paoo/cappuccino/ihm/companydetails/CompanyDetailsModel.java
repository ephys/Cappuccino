package paoo.cappuccino.ihm.companydetails;

import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class CompanyDetailsModel extends BaseModel{


  private ICompanyDto companyDto;
  private List<IContactDto> contactDto;


  public void setCompanyDto(ICompanyDto companyDto){

    this.companyDto = companyDto;
  }

  public void setContactDto(List<IContactDto> contactDto){

    this.contactDto = contactDto;

  }

  public ICompanyDto getCompanyDto() {

    return companyDto;
  }


  public List<IContactDto> getContactDto() {

    return contactDto;
  }

}
