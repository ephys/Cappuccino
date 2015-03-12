package paoo.cappuccino.ucc;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;

/**
 * Use case controller containing methods relative to a contact as an entity.
 *
 * @author Laurent
 */
public interface IContactUcc {

  /**
   * Register a contact in the database.
   * 
   * @param company The compagny from which come the contact.
   * @param email The contact's mail, set valid by default.
   * @param firstName The contact's first name.
   * @param lastName The contact's last name.
   * @param phone The contact's phone number.
   * @return The new contact's DTO.
   * @throws java.lang.IllegalArgumentException The email is not valid.
   * 
   */
  public IContactDto add(ICompanyDto company, String email, String firstName, String lastName,
      String phone);

  /**
   * Set The contact's email to an invalid state.
   * 
   * @param contact The contact which need to have his email invalidated.
   * @return The modified contact's DTO.
   */
  public IContactDto setMailInvalid(IContactDto contact);

  /**
   * Return a table with all the contacts which matches the research data
   * 
   * @param firstName First name of the contact search.
   * @param lastName Last name of the contact search.
   * @return A table with the contact's DTO researched.
   */
  public IContactDto[] getContacts(String firstName, String lastName);

}
