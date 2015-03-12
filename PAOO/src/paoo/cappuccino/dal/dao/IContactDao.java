package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;

public interface IContactDao {

  /**
   * Insert a new contact in the database.
   *
   * @param contact The contact to insert.
   * @return the contact entity with its information updated from the database.
   * @throws java.lang.IllegalArgumentException                One of the fields failed to insert.
   *                                                           due to constraint violations.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error.
   */
  IContactDto createContact(IContactDto contact);

  void updateContact(IContactDto contact);

  /**
   * Fetches every contact that matches the given first name and/or last name.
   *
   * @param firstName The first name of the contact, nullable.
   * @param lastName  The last name of the contact, nullable.
   * @return The list of contact matching the first name and/or the last name.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error.
   */
  /* TODO Eager loading: Automatically fetches the contact's company (with the same query). */
  IContactDto[] fetchContactByName(String firstName, String lastName);

  /**
   * Fetches all contacts working for a given company.
   *
   * @param company The company.
   * @return The list of contacts working for the company.
   *
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error.
   */
  IContactDto[] fetchContactsByCompany(ICompanyDto company);
}

