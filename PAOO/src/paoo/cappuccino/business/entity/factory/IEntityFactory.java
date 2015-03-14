package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IUserDto;
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
   * @param username  The user's username, must be unique
   * @param password  A hashed valid password
   * @param lastName  The user's last name
   * @param firstName The user's first name
   * @param email     The user's email, must be a valid email.
   * @param role      The user's role
   */
  IUser createUser(String username, IHashHolderDto password, String lastName, String firstName,
                   String email, IUserDto.Role role);

  /**
   * Creates an already existing user entity, for the DAL.
   *
   * @param id           The user's identifier
   * @param version      The user version in the database
   * @param username     The user's username
   * @param password     The user's password hash holder, see
   *                     {@link paoo.cappuccino.util.hasher.StringHasher#unserialize(String)}
   *                     for details on how to get that object
   * @param lastName     The user's last name
   * @param firstName    The user's first name
   * @param email        The user's email, must be valid
   * @param role         The user's role
   * @param registerDate The date and time at which the entity was registered
   */
  IUser createUser(int id, int version, String username, IHashHolderDto password, String lastName,
                   String firstName, String email, IUserDto.Role role, LocalDateTime registerDate);

  /**
   * Creates a new Contact entity (as in not yet stored), for the UCC.
   *
   * @param companyId The identifier of the company the entity is working for.
   * @param lastName  The contact's last name.
   * @param firstName The contact's first name.
   * @param email     The contact's email, nullable.
   * @param phone     The contact's phone, nullable.
   */
  IContact createContact(int companyId, String email, String firstName, String lastName,
                         String phone);

  /**
   * Creates an already existing contact entity, for the DAL.
   *
   * @param id         The contact's identifier
   * @param version    The contact version in the database
   * @param companyId  The identifier of the company the entity is working for.
   * @param lastName   The contact's last name.
   * @param firstName  The contact's first name.
   * @param email      The contact's email, nullable.
   * @param emailValid Whether or not the email of the contact is valid.
   * @param phone      The contact's phone, nullable.
   */
  IContact createContact(int id, int version, int companyId, String email, boolean emailValid,
                         String firstName, String lastName, String phone);

  /**
   * TODO. version UCC
   */
  ICompany createCompany(int creatorId, String name, String addressStreet, String addressNum,
                         String addressMailbox, String addressPostcode, String addressTown);

  /**
   * TODO. version DAL
   */
  ICompany createCompany(int id, int version, int creatorId, String name,
                         String addressStreet, String addressNum, String addressMailbox,
                         String addressPostcode,
                         String addressTown);

  /**
   * TODO. version UCC
   */
  IBusinessDay createBusinessDay(LocalDateTime eventDate);

  /**
   * TODO. version DAL
   */
  IBusinessDay createBusinessDay(int id, int version, LocalDateTime creationDate,
                                 LocalDateTime eventDate);

  /**
   * TODO. version UCC
   */
  IParticipation createParticipation(int companyId, int businessDayId);

  /**
   * TODO. version DAL
   */
  IParticipation createParticipation(int companyId, int businessDayId, int version,
                                     IParticipationDto.State state, boolean cancelled);
}
