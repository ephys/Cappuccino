package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.config.injector.Singleton;

@Singleton
public interface IUserDao {

  /**
   * Inserts a new user in the database.
   *
   * @param user The user to insert.
   * @return The user entity with its informations updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueEntityException The entity already exists in the
   *                                                                database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException  The username is not unique.
   * @throws java.lang.IllegalArgumentException                     One of the fields failed to
   *                                                                insert due to constraint
   *                                                                violations.
   */
  IUser createUser(IUserDto user);

  /**
   * Fetches the matching user in the database.
   *
   * @param username the user's username.
   * @return the matching user or null if none was found.
   * @throws paoo.cappuccino.dal.exception.ConnectionException a connection error occurred.
   */
  IUser fetchUserByUsername(String username);
}
