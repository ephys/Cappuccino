package paoo.cappuccino.ihm.contactdetails;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class ContactDetailsModel extends BaseModel{
  
  private boolean invalidMail;
  private IContactDto contactDto;
  
  public void setInvalidMail(boolean b){
    
    this.invalidMail = b;
    dispatchChangeEvent();
  }
  
  public void setContactDto(IContactDto contactDto){
    
   this.contactDto = contactDto;
  }
  
 public IContactDto getContactDto(){
    
    return this.contactDto;
  }
 
  public boolean isInvalidMail(){
    
    return this.invalidMail;
  }
  
  
}
