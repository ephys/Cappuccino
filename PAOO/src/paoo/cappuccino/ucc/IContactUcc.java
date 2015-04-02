package paoo.cappuccino.ucc;

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
   * @param company The id of the company the contact is working for.
   * @param email The contact's email, nullable. Valid by default.
   * @param firstName The contact's first name.
   * @param lastName The contact's last name.
   * @param phone The contact's phone number, nullable.
   * @return The new contact's DTO.
   * @throws java.lang.IllegalArgumentException The email does not follow a valid email format or
   *         one of the non-nullable fields is empty.
   */
  public IContactDto create(int company, String email, String firstName,
      String lastName, String phone);

  /**
   * Set a contact's email as being invalid.
   *
   * @param contact The contact who needs to have their email invalidated.
   * @return true: the email vas set as invalid, false: the email is not set.
   */
  public boolean setMailInvalid(IContactDto contact);

  /**
   * Returns a table with all the contacts which matches the research data.
   *
   * @param firstName First name of the contact search.
   * @param lastName Last name of the contact search.
   * @return A table with the contact's DTO researched.
   */
  public IContactDto[] searchContact(String firstName, String lastName);

  /**
   *
   * @param id
   * @return
   */
  public IContactDto[] getContactByCompany(int id);

}
