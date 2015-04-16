package paoo.cappuccino.ucc.impl;

import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.ICompanyUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;

class CompanyUcc implements ICompanyUcc {

  private final IEntityFactory factory;
  private final ICompanyDao companyDao;

  @Inject
  public CompanyUcc(IEntityFactory entityFactory, ICompanyDao companyDao) {
    this.factory = entityFactory;
    this.companyDao = companyDao;
  }

  @Override
  public ICompanyDto create(IUserDto creator, String name, String street, String numAddress,
                            String mailBox, String postCode, String town) {
    ValidationUtil.ensureNotNull(creator, "creator");
    ValidationUtil.ensureFilled(name, "name");
    ValidationUtil.ensureFilled(street, "street");
    ValidationUtil.ensureFilled(numAddress, "numAddress");
    ValidationUtil.ensureFilled(postCode, "postCode");
    ValidationUtil.ensureFilled(town, "town");

    if (StringUtils.isEmpty(mailBox)) {
      mailBox = null;
    }

    ICompanyDto dto =
        factory.createCompany(creator.getId(), name, street, numAddress, mailBox, postCode, town);

    try {
      return companyDao.createCompany(dto);
    } catch (NonUniqueFieldException e) {
      throw new IllegalArgumentException("There is already a company registered with that name.");
    }
  }

  @Override
  public List<ICompanyDto> searchCompanies(String name, String postCode, String town,
                                           String street) {
    if (StringUtils.isEmpty(street)) {
      street = null;
    }

    if (StringUtils.isEmpty(town)) {
      town = null;
    }

    if (StringUtils.isEmpty(postCode)) {
      postCode = null;
    }

    if (StringUtils.isEmpty(name)) {
      name = null;
    }

    return companyDao.searchCompanies(name, postCode, street, town);
  }

  @Override
  public List<ICompanyDto> getInvitableCompanies() {
    return companyDao.fetchInvitableCompanies();
  }

  @Override
  public List<ICompanyDto> getAllCompanies() {
    return companyDao.fetchAll();
  }

  @Override
  public ICompanyDto getCompanyById(int company) {
    if (company <= 0) {
      throw new IllegalArgumentException("invalid id");
    }
    return companyDao.fetchCompanyById(company);
  }

  @Override
  public List<ICompanyDto> getCompaniesByDay(int dayid) {
    if (dayid <= 0) {
      throw new IllegalArgumentException("invalid id");
    }
    return companyDao.fetchCompaniesByDay(dayid);
  }
}
