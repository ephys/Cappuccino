package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.dao.IContactDao;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.util.ValidationUtil;

class CompanyUcc implements ICompanyUcc {

  private final IEntityFactory factory;
  private final ICompanyDao dao;
  private final IContactDao contactDao;

  @Inject
  public CompanyUcc(IEntityFactory entityFactory, IDalService dalService, ICompanyDao companyDao,
      IContactDao cDao) {
    this.factory = entityFactory;
    this.dao = companyDao;
    this.contactDao = cDao;
  }

  @Override
  public ICompanyDto create(IUserDto creator, String name, String street, String numAdress,
      String mailBox, String postCode, String town) {
    if (contactDao.fetchContactByName(creator.getFirstName(), creator.getLastName()) == null) {
      throw new IllegalArgumentException("User not found");
    }
    ValidationUtil.ensureNotNull(creator, "creator");
    ValidationUtil.ensureNotNull(name, "name");
    ValidationUtil.ensureNotNull(street, "street");
    ValidationUtil.ensureNotNull(numAdress, "numAdress");
    ValidationUtil.ensureNotNull(mailBox, "mailBox");
    ValidationUtil.ensureNotNull(postCode, "postCode");
    ValidationUtil.ensureNotNull(town, "town");
    ValidationUtil.ensureFilled(name, "name");
    ValidationUtil.ensureFilled(street, "street");
    ValidationUtil.ensureFilled(numAdress, "numAdress");
    ValidationUtil.ensureFilled(mailBox, "mailBox");
    ValidationUtil.ensureFilled(postCode, "postCode");
    ValidationUtil.ensureFilled(town, "town");
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
