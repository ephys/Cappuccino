package paoo.cappuccino.ucc;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.config.injector.DependencyInjector;

/**
 * Use case controller containing methods relative to an user (as en entity, not as an actor)
 *
 * @author Guylian Cox
 */
public interface IUserUcc {
  /**
   * Registers an user in the system
   *
   * @param username  The user's username. Used for logging in, must be unique and is case-insensitive.
   * @param password  The user's password.
   * @param firstName The user's first name.
   * @param lastName  The user's last name.
   * @param email     A valid email.
   * @return The registered user's DTO
   *
   * @throws java.lang.IllegalArgumentException The username isn't unique or the email
   *                                            is not invalid.
   */
  public IUserDto register(String username, String password, String firstName, String lastName,
                           String email);

  /**
   * Logs an user into the system.
   *
   * @param username The user's username. The input is case-insensitive.
   * @param password The user's password.
   * @return The logged user's DTO or null if the username/password combination is incorrect.
   */
  public IUserDto logIn(String username, String password);
}