package paoo.cappuccino.dal.dao;

import java.time.LocalDateTime;

import paoo.cappuccino.core.injector.Singleton;

@Singleton
public interface ICompanyDao {

  //TODO JavaDoc
  ICompanyDto createCompany(ICompanyDto company);

  //TODO JavaDoc
  IParticipation[] fetchParticipationByDate(LocalDateTime time);

  //TODO JavaDoc
  ICompanyDto fetchCompanyByName(String name, String postcode, String street, String town);

}
