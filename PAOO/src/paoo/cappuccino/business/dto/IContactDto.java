package paoo.cappuccino.business.dto;

/**
 * Data transfer object for the Contact entity.
 *
 * @author Nicolas Fischer
 */
public interface IContactDto extends IBaseDto {

  /**
   * Gets the contact's company id.
   */
  int getCompany();

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
}
