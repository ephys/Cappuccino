package paoo.cappuccino.ucc.mock;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.ucc.IContactUcc;

//TODO
public class MockContactUcc implements IContactUcc {

  @Override
  public IContactDto create(ICompanyDto company, String email, String firstName, String lastName,
                            String phone) {
    return null;
  }

  @Override
  public boolean setMailInvalid(IContactDto contact) {
    return false;
  }

  @Override
  public IContactDto[] searchContact(String firstName, String lastName) {
    return new IContactDto[0];
  }
}
