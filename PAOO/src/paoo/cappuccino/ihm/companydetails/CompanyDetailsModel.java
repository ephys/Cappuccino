package paoo.cappuccino.ihm.companydetails;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class CompanyDetailsModel extends BaseModel{
  
  
  private ICompanyDto companyDto;
  private IContactDto[] contactDto;
  
  
  public void setCompanyDto(ICompanyDto companyDto){
    
    this.companyDto = companyDto;
  }
  
  public void setContactDto(IContactDto[] contactDto){
    
    this.contactDto = contactDto;
  }
  
 public void fireDispatchChangeEvent(){ 
   
   dispatchChangeEvent();
 }
  public ICompanyDto getCompanyDto() {

    return companyDto;
  }
 
  
  public IContactDto[] getContactDto() {

    return contactDto;
  }

}
