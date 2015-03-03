package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.config.injector.Singleton;

/**
 * Interface used to create new instances of various business entities
 *
 * @author Nicolas Fischer
 */
@Singleton
public interface IEntityFactory {

  /**
   * Creates a new user entity (as in not yet stored)
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
   * Creates an already existing user entity
   *
   * @param id           The entity's identifier
   * @param version      The entity version in the database
   * @param username     The entity's username
   * @param password     The entity's password
   * @param hash         The entity's password hash
   * @param lastName     The entity's last name
   * @param firstName    The entity's first name
   * @param email        The entity's email, must be valid
   * @param role         The entity's role
   * @param registerDate The date and time at which the entity was registered
   */
  IUser createUser(int id, int version, String username, byte[] password, byte[] hash,
                   String lastName, String firstName,
                   String email, IUserDto.Role role, LocalDateTime registerDate);
}
