package paoo.cappuccino.dal.dao;

import paoo.cappuccino.core.injector.Singleton;

@Singleton
public interface IContactDao {

  //TODO JavaDoc
  IContactDto createContact(IContactDto contact);

  //TODO JavaDoc
  IContactDto[] fetchContactsByCompany(int company);

  //TODO JavaDoc
  IContactDto[] fetchContactByName(String firstName, String lastName);

}
