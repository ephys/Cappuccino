package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.impl.Company;
import paoo.cappuccino.core.injector.Singleton;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Interface used to create new instances of various business entities.
 *
 * @author Nicolas Fischer
 */
@Singleton
public interface IEntityFactory {

  /**
   * Creates a new user entity (as in not yet stored), for the UCC.
   *
   * @param username The entity's username, must be unique
   * @param password A hashed valid password
   * @param lastName The entity's last name
   * @param firstName The entity's first name
   * @param email The entity's email, must be valid
   * @param role The entity's role
   */
  IUser createUser(String username, IHashHolderDto password, String lastName, String firstName,
      String email, IUserDto.Role role);

  /**
   * Creates an already existing user entity, for the DAL.
   *
   * @param id The entity's identifier
   * @param version The entity version in the database
   * @param username The entity's username
   * @param password The entity's password hash holder, see
   *        {@link paoo.cappuccino.util.hasher.StringHasher#unserialize(String)} for details on how
   *        to get that object
   * @param lastName The entity's last name
   * @param firstName The entity's first name
   * @param email The entity's email, must be valid
   * @param role The entity's role
   * @param registerDate The date and time at which the entity was registered
   */
  IUser createUser(int id, int version, String username, IHashHolderDto password, String lastName,
      String firstName, String email, IUserDto.Role role, LocalDateTime registerDate);

  /**
   * Creates a new Contact entity (as in not yet stored), for the UCC.
   *
   * @param company The entity's company referd a existing company
   * @param lastName The entity's last name
   * @param firstName The entity's first name
   * @param email The entity's email, must be valid
   * @param role The entity's role
   * @param phone The entity's phone, must be valid
   */
  IContact createContact(Company company, String email, String firstName, String lastName,
      String phone);

  /**
   * Creates an already existing contact entity, for the DAL.
   * 
   * @param id The entity's identifier
   * @param version The entity version in the database
   * @param company The entity's company referd a existing company
   * @param lastName The entity's last name
   * @param firstName The entity's first name
   * @param email The entity's email, must be valid
   * @param role The entity's role
   * @param phone The entity's phone, must be valid
   */
  IContact createContact(int idContact, int version, Company company, String email,
      String firstName, String lastName, String phone);

  /**
   * 
   * @param creator
   * @param name
   * @param address_street
   * @param addressNum
   * @param addressMailbox
   * @param addressPostcode
   * @param addressTown
   * @return
   */
  ICompany createCompany(IUserDto creator, String name, String address_street, String addressNum,
      String addressMailbox, String addressPostcode, String addressTown);

  /**
   * 
   * @param idCompany
   * @param version
   * @param creator
   * @param name
   * @param address_street
   * @param addressNum
   * @param addressMailbox
   * @param addressPostcode
   * @param addressTown
   * @return
   */
  ICompany createCompany(int idCompany, int version, IUserDto creator, String name,
      String address_street, String addressNum, String addressMailbox, String addressPostcode,
      String addressTown);

  /**
   * 
   * @param eventDate
   * @param academicYear
   * @return
   */
  IBusinessDay createBusinessDay(LocalDateTime eventDate, String academicYear);

  /**
   * 
   * @param idBusinessDay
   * @param version
   * @param creationDate
   * @param eventDate
   * @param academicYear
   * @return
   */
  IBusinessDay createBusinessDay(int idBusinessDay, int version, LocalDateTime creationDate,
      LocalDateTime eventDate, String academicYear);
}
