package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IBaseDto;
import paoo.cappuccino.business.dto.IContactDto;

/**
 * Interface containing business methods relative to the contact entity.
 *
 * @author Nicolas Fischer
 */
public interface IContact extends IContactDto, IBaseDto, IVersionedEntity {
  /**
   * Sets the contact mail. If the given email is not null, it will be considered valid and
   * {@link IContactDto#isEmailValid()} will return true again.
   *
   * @param email A valid email, nullable.
   */
  void setEmail(String email);

  /**
   * Sets the contact mail validity.
   *
   * @param emailValid true: the email is valid..
   */
  void setEmailValid(boolean emailValid);

  /**
   * Sets the contact phone number.
   *
   * @param phone A phone number, nullable.
   */
  void setPhone(String phone);

  /**
   * Sets the contact's last name.
   *
   * @param lastName The contact's last name.
   */
  void setLastName(String lastName);

  /**
   * Sets the contact's first name.
   *
   * @param firstName The contact's first name.
   */
  void setFirsName(String firstName);

  /**
   * Sets the company.
   *
   * @param company The id of the company.
   */
  void setCompany(int company);
}
