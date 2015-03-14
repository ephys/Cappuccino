package paoo.cappuccino.ucc.mock;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ucc.ICompanyUcc;

// TODO
public class MockCompanyUcc implements ICompanyUcc {

  @Override
  public ICompanyDto create(IUserDto creator, String name, String street, String numAdress,
                            String mailBox, String postCode, String town) {
    return null;
  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postCode, String town, String street) {
    return new ICompanyDto[0];
  }

  @Override
  public ICompanyDto[] getInvitableCompanies() {
    return new ICompanyDto[0];
  }
}
