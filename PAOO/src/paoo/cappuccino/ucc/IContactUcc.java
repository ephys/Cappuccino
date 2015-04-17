package paoo.cappuccino.ucc;

import java.util.List;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Use case controller containing methods relative to a contact as an entity.
 *
 * @author Laurent
 */
public interface IContactUcc {

  /**
   * Creates a contact and persists it.
   *
   * @param company   The id of the company the contact is working for.
   * @param email     The contact's email, nullable. Valid by default.
   * @param firstName The contact's first name.
   * @param lastName  The contact's last name.
   * @param phone     The contact's phone number, nullable.
   * @return The new contact's DTO.
   * @throws java.lang.IllegalArgumentException The email does not follow a valid email format or
   *                                            one of the non-nullable fields is empty.
   */
  IContactDto create(int company, String email, String firstName, String lastName, String phone);

  /**
   * Set a contact's email as being invalid.
   *
   * @param contact The contact who needs to have their email invalidated.
   * @return true: the email vas set as invalid, false: the email is not set.
   */
  boolean setMailInvalid(IContactDto contact);

  /**
   * Searches contacts based on their names.
   *
   * @param firstName First name of the contact search. Nullable.
   * @param lastName  Last name of the contact search. Nullable.
   * @return the list of contacts matching the criteria.
   */
  List<IContactDto> searchContact(String firstName, String lastName);

  /**
   * Get all the contacts of a company.
   *
   * @param id The id of the company for which the contacts are needed. Returns the list of contacts
   *           working for a given company.
   */
  List<IContactDto> getContactByCompany(int id);
}
