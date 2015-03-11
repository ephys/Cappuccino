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
}
