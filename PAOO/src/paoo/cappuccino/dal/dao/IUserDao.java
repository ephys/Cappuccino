package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;

public interface IUserDao {

  /**
   * Inserts a new user in the database.
   *
   * @param user The user to insert.
   * @return The user entity with its informations updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The username is not unique.
   * @throws java.lang.IllegalArgumentException One of the fields failed to insert due to constraint
   *         violations.
   * @throws paoo.cappuccino.dal.exception.FatalException Database connection error
   */
  IUserDto createUser(IUserDto user);

  /**
   * Fetches the matching user in the database.
   *
   * @param username the user's username.
   * @return the matching user or null if none was found.
   * @throws paoo.cappuccino.dal.exception.FatalException a database connection error occurred.
   */
  IUserDto fetchUserByUsername(String username);

  /**
   * Updates an user in the database.
   *
   * @param user A user entity.
   * @throws java.util.ConcurrentModificationException the entity version did not match or the
   *         entity has been deleted.
   * @throws java.lang.IllegalArgumentException the entity hasn't been inserted in the database yet.
   *         Or one of the fields failed to insert to to constraint violations.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The username is not unique.
   * @throws paoo.cappuccino.dal.exception.FatalException Database connection error
   */
  void updateUser(IUser user);

  IUserDto getCompanyCreator(int companyId);
}
