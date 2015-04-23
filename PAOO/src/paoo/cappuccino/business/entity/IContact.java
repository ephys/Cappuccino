package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IContactDto;

/**
 * Interface containing business methods relative to the contact entity.
 *
 * @author Nicolas Fischer
 */
public interface IContact extends IContactDto, IBaseEntity {
  /**
   * Sets the contact mail.
   *
   * @param email A String.
   */
  void setEmail(String email);

  /**
   * Sets the contact mail validity.
   *
   * @param emailValid A boolean.
   */
  void setEmailValid(boolean emailValid);

  /**
   * Sets the contact phone.
   *
   * @param phone A String.
   */
  void setPhone(String phone);

  /**
   * Sets the last name.
   * 
   * @param lastName A string.
   */
  void setLastName(String lastName);

  /**
   * Sets the first name.
   * 
   * @param firstName A string.
   */
  void setFirsName(String firstName);

  /**
   * Sets the company.
   * 
   * @param company An integer, the id of the company.
   */
  void setCompany(int company);
}
