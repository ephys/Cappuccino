package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IAttendance;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Interface used to create new instances of various business entities.
 *
 * @author Nicolas Fischer
 */
public interface IEntityFactory {

  /**
   * Creates a new user entity (as in not yet stored), for the UCC.
   *
   * @param username The user's username, must be unique.
   * @param password A hashed valid password.
   * @param lastName The user's last name.
   * @param firstName The user's first name.
   * @param email The user's email, must be a valid email.
   * @param role The user's role.
   */
  IUser createUser(String username, char[] password, String lastName, String firstName,
      String email, IUserDto.Role role);

  /**
   * Creates an already existing user entity, for the DAL.
   *
   * @param id The user's identifier.
   * @param version The user version in the database.
   * @param username The user's username.
   * @param password The user's password hash holder, see
   *        {@link paoo.cappuccino.util.hasher.StringHasher#deserialize(String)} for details on how
   *        to get that object.
   * @param lastName The user's last name.
   * @param firstName The user's first name.
   * @param email The user's email, must be valid.
   * @param role The user's role.
   * @param registerDate The date and time at which the entity was registered.
   */
  IUser createUser(int id, int version, String username, IHashHolderDto password, String lastName,
      String firstName, String email, IUserDto.Role role, LocalDateTime registerDate);

  /**
   * Creates a new contact entity (as in not yet stored), for the UCC.
   *
   * @param companyId The identifier of the company the entity is working for.
   * @param lastName The contact's last name.
   * @param firstName The contact's first name.
   * @param email The contact's email, nullable.
   * @param phone The contact's phone, nullable.
   */
  IContact createContact(int companyId, String email, String firstName, String lastName,
      String phone);

  /**
   * Creates an already existing contact entity, for the DAL.
   *
   * @param id The contact's identifier.
   * @param version The contact version in the database.
   * @param companyId The identifier of the company the entity is working for.
   * @param lastName The contact's last name.
   * @param firstName The contact's first name.
   * @param email The contact's email, nullable.
   * @param emailValid Whether or not the email of the contact is valid.
   * @param phone The contact's phone, nullable.
   */
  IContact createContact(int id, int version, int companyId, String email, boolean emailValid,
      String firstName, String lastName, String phone);

  /**
   * Creates a new company entity (as in not yet stored), for the UCC.
   *
   * @param creatorId The identifier of the user who create the company .
   * @param name The company's name.
   * @param addressStreet The address street of the company.
   * @param addressNum The address number of the company.
   * @param addressMailbox The address mail box of the company.
   * @param addressPostcode The address post code of the company.
   * @param addressTown The address town of the company.
   */
  ICompany createCompany(int creatorId, String name, String addressStreet, String addressNum,
      String addressMailbox, String addressPostcode, String addressTown);

  /**
   * Creates an already existing company entity, for the DAL.
   *
   * @param id The company's identifier.
   * @param version The company version in the database.
   * @param creatorId The identifier of the user who create the company .
   * @param name The company's name.
   * @param addressStreet The address street of the company.
   * @param addressNum The address number of the company.
   * @param addressMailbox The address mail box of the company.
   * @param addressPostcode The address post code of the company.
   * @param addressTown The address town of the company.
   * @param registerDate The date and time at which the entity was registered.
   */
  ICompany createCompany(int id, int version, int creatorId, String name, String addressStreet,
      String addressNum, String addressMailbox, String addressPostcode, String addressTown,
      LocalDateTime registerDate);

  /**
   * Creates a new businessDay entity (as in not yet stored), for the UCC.
   *
   * @param eventDate The date of the businessDay will take place.
   */
  IBusinessDay createBusinessDay(LocalDateTime eventDate);


  /**
   * Creates an already existing businessDay entity, for the DAL.
   *
   * @param id The businessDay's identifier.
   * @param version The businessDay version in the database.
   * @param eventDate The date of the businessDay will take place.
   * @param creationDate The creation date of the businessDay
   */
  IBusinessDay createBusinessDay(int id, int version, LocalDateTime eventDate,
      LocalDateTime creationDate);

  /**
   * Creates a new participation entity (as in not yet stored), for the UCC.
   *
   * @param companyId the identifier of the company attending the business day.
   * @param businessDayId the identifier of the business day the company is attending.
   */
  IParticipation createParticipation(int companyId, int businessDayId);

  /**
   * Creates an already existing participation entity, for the DAL.
   *
   * @param companyId the identifier of the company attending the business day.
   * @param businessDayId the identifier of the business day the company is attending.
   * @param version The version of participation in the database.
   * @param state The state of the participation.
   * @param cancelled The participation has been cancelled.
   */
  IParticipation createParticipation(int companyId, int businessDayId, boolean cancelled,
                                     int version, IParticipationDto.State state);

  /**
   * Creates a new attendance.
   *
   * @param companyId the identifier of the company attending the business day.
   * @param businessDayId the identifier of the business day the company is attending.
   * @param contactId the identifier of the contact attending the business day.
   */
  IAttendance createAttendance(int companyId, int businessDayId, int contactId);
}
