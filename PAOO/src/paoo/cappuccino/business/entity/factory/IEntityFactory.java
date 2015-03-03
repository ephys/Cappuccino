package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.config.injector.Singleton;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Interface used to create new instances of various business entities
 *
 * @author Nicolas Fischer
 */
@Singleton
public interface IEntityFactory {

  /**
   * Creates a new user entity (as in not yet stored), for the UCC
   *
   * @param username  The entity's username, must be unique
   * @param password  A valid password
   * @param lastName  The entity's last name
   * @param firstName The entity's first name
   * @param email     The entity's email, must be valid
   * @param role      The entity's role
   */
  IUser createUser(String username, String password, String lastName, String firstName,
                   String email, IUserDto.Role role);

  /**
   * Creates an already existing user entity, for the DAL
   *
   * @param id           The entity's identifier
   * @param version      The entity version in the database
   * @param username     The entity's username
   * @param password     The entity's password hash holder, see {@link paoo.cappuccino.util.hasher.StringHasher#unserialize(String)}
   *                     for details on how to get that object
   * @param lastName     The entity's last name
   * @param firstName    The entity's first name
   * @param email        The entity's email, must be valid
   * @param role         The entity's role
   * @param registerDate The date and time at which the entity was registered
   */
  IUser createUser(int id, int version, String username, IHashHolderDto password,
                   String lastName, String firstName, String email, IUserDto.Role role,
                   LocalDateTime registerDate);
}
