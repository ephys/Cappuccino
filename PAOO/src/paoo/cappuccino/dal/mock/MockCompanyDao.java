package paoo.cappuccino.dal.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.dao.IParticipationDao;

/**
 * Mock implementation of the company dao.
 */
class MockCompanyDao implements ICompanyDao {

  private final IEntityFactory factory;
  private final IParticipationDao participationDao;
  private final IBusinessDayDao businessDayDao;
  private final List<ICompany> companyList = new ArrayList<>();

  @Inject
  public MockCompanyDao(IEntityFactory factory, IParticipationDao participationDao,
                        IBusinessDayDao businessDayDao) {
    this.factory = factory;
    this.participationDao = participationDao;
    this.businessDayDao = businessDayDao;

    createCompany(factory.createCompany(1, "Coca-cola", "rue du coca", "5", null, "1020",
                                        "Ville de la boisson"));
    createCompany(factory.createCompany(2, "Uniway", "rue du web", "4", null, "1310",
                                        "Ville du réseau"));
    createCompany(factory.createCompany(3, "windows", "rue des os", "25", null, "1000",
                                        "Ville d'operating system"));
    createCompany(factory.createCompany(4, "appel", "rue des os", "3", "b", "1000",
                                        "Ville d'operating system"));
  }

  @Override
  public ICompanyDto createCompany(ICompanyDto company) {
    ICompany newCompany =
        factory.createCompany(companyList.size() + 1, 1, company.getCreator(), company.getName(),
                              company.getAddressStreet(), company.getAddressNum(),
                              company.getAddressMailbox(),
                              company.getAddressPostcode(), company.getAddressTown(),
                              company.getRegisterDate());

    companyList.add(newCompany);

    return newCompany;
  }

  @Override
  public void updateCompany(ICompanyDto company) {
    if (companyList.size() > company.getId()
        || companyList.get(company.getId() - 1).getVersion() != company.getVersion()) {
      throw new ConcurrentModificationException();
    }

    ICompany companyEntity = factory.createCompany(company.getId(), company.getVersion() + 1,
                                                   company.getCreator(),
                                                   company.getName(), company.getAddressStreet(),
                                                   company.getAddressNum(),
                                                   company.getAddressMailbox(),
                                                   company.getAddressPostcode(),
                                                   company.getAddressTown(),
                                                   company.getRegisterDate());

    companyList.set(company.getId() - 1, companyEntity);

    if (company instanceof ICompany) {
      ((ICompany) company).incrementVersion();
    }
  }

  @Override
  public List<ICompanyDto> searchCompanies(String name, String postcode, String street,
                                           String town) {
    List<ICompanyDto> toReturn = new ArrayList<>();

    for (ICompany company : companyList) {
      if (name != null && !company.getName().equalsIgnoreCase(name)) {
        continue;
      }

      if (postcode != null && !company.getAddressPostcode().equalsIgnoreCase(postcode)) {
        continue;
      }

      if (street != null && !company.getAddressStreet().equalsIgnoreCase(street)) {
        continue;
      }

      if (town != null && !company.getAddressTown().equalsIgnoreCase(town)) {
        continue;
      }

      toReturn.add(company);
    }

    return toReturn;
  }


  @Override
  public List<ICompanyDto> fetchAll() {
    return Collections.unmodifiableList(companyList);
  }

  @Override
  public List<ICompanyDto> fetchInvitableCompanies() {
    List<ICompanyDto> toReturn = new ArrayList<>();
    for (ICompany searchee : companyList) {
      if (searchee.getRegisterDate().isAfter(LocalDateTime.now().minusYears(1))) {
        toReturn.add(searchee);
        continue;
      }

      List<IParticipationDto> participationList =
          participationDao.fetchParticipationsByCompany(searchee.getId());

      for (IParticipationDto participation : participationList) {
        if (participation.getState() != State.PAID) {
          continue;
        }

        IBusinessDayDto businessDay =
            businessDayDao.fetchBusinessDaysByDate(participation.getBusinessDay());

        if (businessDay.getEventDate().isAfter(LocalDateTime.now().minusYears(4))) {
          toReturn.add(searchee);
          break;
        }
      }
    }

    return toReturn;
  }

  @Override
  public List<ICompanyDto> fetchCompaniesByDay(int businessDayId) {
    List<ICompanyDto> toReturn = new ArrayList<>();

    for (ICompany company : companyList) {
      List<IParticipationDto> companyParticipations =
          participationDao.fetchParticipationsByCompany(company.getId());

      for (IParticipationDto participation : companyParticipations) {
        if (participation.getBusinessDay() == businessDayId) {
          toReturn.add(company);
          break;
        }
      }
    }

    return toReturn;
  }

  @Override
  public ICompanyDto fetchCompanyById(int id) {
    for (ICompanyDto company : companyList) {
      if (company.getId() == id) {
        return company;
      }
    }

    return null;
  }
}
