package paoo.cappuccino.business.dto;

import paoo.cappuccino.business.entity.ICompany;


/**
 * Data transfer object for the Contact entity.
 *
 * @author Nicolas Fischer
 */
public interface IContactDto {
  /**
   * Gets the contact's compagny.
   */
  ICompany getCompany();

  /**
   * Gets the contact's email.
   */
  String getEmail();

  /**
   * Gets the contact's email validity.
   */
  boolean isEmailValid();

  /**
   * Gets the contact's first name.
   */
  String getFirstName();

  /**
   * Gets the contact's last name.
   */
  String getLastName();

  /**
   * Gets the contact's phone.
   */
  String getPhone();

  /**
   * Gets the contact's phone.
   */
  IParticipationDto[] getParticipations();
}
