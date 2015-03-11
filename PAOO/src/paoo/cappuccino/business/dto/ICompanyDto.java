package paoo.cappuccino.business.dto;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;

/**
 * Data transfer object for the Compagny entity.
 *
 * @author Nicolas Fischer
 */
public interface ICompanyDto {
  /**
   * Gets the company's creator.
   */
  IUser GetCreator();

  /**
   * Gets the company's name.
   */
  String getName();

  /**
   * Gets the company's register date.
   */
  LocalDateTime getRegisterDate();

  /**
   * Gets the company's address street.
   */
  String getAddressStreet();

  /**
   * Gets the company's address num.
   */
  String getAddressNum();

  /**
   * Gets the company's address mailbox.
   */
  String getAddressMailbox();

  /**
   * Gets the company's address postcode.
   */
  String getAddressPostcode();

  /**
   * Gets the company's address town.
   */
  String getAddressTown();

  /**
   * Gets the list of business day this company (will attend) attended.
   */
  IParticipationDto[] getParticipations();
}
