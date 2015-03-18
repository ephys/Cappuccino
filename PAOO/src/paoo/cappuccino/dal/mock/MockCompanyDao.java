package paoo.cappuccino.dal.mock;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.dal.dao.ICompanyDao;

class MockCompanyDao implements ICompanyDao {

  @Override
  public ICompanyDto createCompany(ICompanyDto company) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateCompany(ICompanyDto company) {
    // TODO Auto-generated method stub

  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postcode, String street, String town) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompanyDto[] fetchAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompanyDto[] fetchInvitableCompanies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompanyDto[] fetchCompaniesByDay(int businessDayId) {
    // TODO Auto-generated method stub
    return null;
  }

}
