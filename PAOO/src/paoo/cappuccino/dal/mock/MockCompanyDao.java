package paoo.cappuccino.dal.mock;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.ICompanyDao;

class MockCompanyDao implements ICompanyDao {
  private List<ICompany> companyList = new ArrayList<>();
  private final IEntityFactory factory;
  private final IParticipationDto participation;

  @Inject
  public MockCompanyDao(IEntityFactory factory, IParticipationDto participation) {
    this.factory = factory;
    this.participation = participation;

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
    ICompany companyEntity =
        factory.createCompany(companyList.size() + 1, 1, company.getCreator(), company.getName(),
            company.getAddressStreet(), company.getAddressNum(), company.getAddressMailbox(),
            company.getAddressPostcode(), company.getAddressTown(), company.getRegisterDate());

    companyList.add(companyEntity);
    return companyEntity;
  }

  @Override
  public void updateCompany(ICompanyDto company) {
    if (companyList.size() > company.getId()
        || companyList.get(company.getId() - 1).getVersion() != company.getVersion())
      throw new ConcurrentModificationException();

    ICompany companyEntity =
        factory.createCompany(company.getId(), company.getVersion(), company.getCreator(),
            company.getName(), company.getAddressStreet(), company.getAddressNum(),
            company.getAddressMailbox(), company.getAddressPostcode(), company.getAddressTown(),
            company.getRegisterDate());


    companyList.set(company.getId() - 1, companyEntity);

  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postcode, String street, String town) {
    List<ICompany> toReturn = new ArrayList<>();

    for (ICompany searchee : companyList) {
      if (name != null && !name.equalsIgnoreCase(name))
        continue;

      if (postcode != null && !postcode.equalsIgnoreCase(searchee.getAddressPostcode()))
        continue;

      if (street != null && !street.equalsIgnoreCase(searchee.getAddressStreet()))
        continue;

      if (town != null && !town.equalsIgnoreCase(searchee.getAddressTown()))
        continue;

      toReturn.add(searchee);
    }

    return toReturn.toArray(new ICompanyDto[toReturn.size()]);
  }


  @Override
  public ICompanyDto[] fetchAll() {
    return companyList.toArray(new ICompanyDto[companyList.size()]);
  }

  @Override
  public ICompanyDto[] fetchInvitableCompanies() {
    List<ICompany> toReturn = new ArrayList<>();
    for (ICompany searchee : companyList) {
      if (participation.getCompany() == searchee.getId()
          && participation.getState().equals(State.PAID)) {
        toReturn.add(searchee);
      }
    }
    return null;
  }

  @Override
  public ICompanyDto[] fetchCompaniesByDay(int businessDayId) {
    // TODO
    List<ICompany> toReturn = new ArrayList<>();

    for (ICompany searchee : companyList) {
      if (true)
        toReturn.add(searchee);
    }

    return toReturn.toArray(new ICompanyDto[toReturn.size()]);
  }

}
