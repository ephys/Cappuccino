package paoo.cappuccino.ihm.contactdetails;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.util.BaseModel;

public class ContactDetailsModel extends BaseModel implements Initializable {

  private IContactDto contactDto;

  public IContactDto getContactDto() {
    return this.contactDto;
  }

  @Override
  public void init(Object[] data) {
    if (data == null || data.length == 0) {
      return;
    }

    if (!(data[0] instanceof IContactDto)) {
      throw new IllegalArgumentException("Incorrect initialization data for ContactDetailsModel");
    }

    contactDto = (IContactDto) data[0];

    dispatchChangeEvent();
  }
}