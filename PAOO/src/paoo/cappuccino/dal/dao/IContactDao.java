package paoo.cappuccino.dal.dao;

import java.util.List;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Data access object used to fetch or persist contacts.
 */
public interface IContactDao {

  /**
   * Inserts a new contact in the database.
   *    if (id <= 0) {
      throw new IllegalArgumentException("invalid id");
    }
   * @param contact The contact to insert.
   * @return the contact entity with its information updated from the database.
   * @throws java.lang.IllegalArgumentException            One of the fields failed to insert due to
   *                                                       constraint violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  IContactDto createContact(IContactDto contact);

  /**
   * Updates a contact in the database.
   *
   * @param contact A contact entity.
   * @throws java.util.ConcurrentModificationException     The entity version did not match or the
   *                                                       entity has been deleted.
   * @throws java.lang.IllegalArgumentException            One of the fields failed to insert to to
   *                                                       constraint violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  void updateContact(IContactDto contact);

  /**
   * Fetches every contact that matches the given first name and/or last name.
   *
   * @param firstName The first name of the contact, nullable.
   * @param lastName  The last name of the contact, nullable.
   * @return The list of contact matching the first name and/or the last name.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IContactDto> fetchContactByName(String firstName, String lastName);

  /**
   * Fetches the list of contacts working for a given company.
   *
   * @param companyId The company.
   * @return The list of contacts working for the company.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<IContactDto> fetchContactsByCompany(int companyId);
}

