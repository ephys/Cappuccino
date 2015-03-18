package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.ucc.ICompanyUcc;

public class CompanyUcc implements ICompanyUcc {

  private final IEntityFactory factory;
  private final ICompanyDao dao;

  @Inject
  public CompanyUcc(IEntityFactory entityFactory, IDalService dalService, ICompanyDao companyDao) {
    this.factory = entityFactory;
    this.dao = companyDao;
  }

  @Override
  public ICompanyDto create(IUserDto creator, String name, String street, String numAdress,
      String mailBox, String postCode, String town) {
    ICompanyDto dto =
        factory.createCompany(creator.getId(), name, street, numAdress, mailBox, postCode, town);
    ICompanyDto returnedDto = dao.createCompany(dto);
    return returnedDto;
  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postCode, String town, String street) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompanyDto[] getInvitableCompanies() {
    // TODO Auto-generated method stub
    return null;
  }

}
