package paoo.cappuccino.business.dto;

import java.time.LocalDateTime;

/**
 * Data transfer object for the User entity
 *
 * @author Nicolas Fischer
 */
public interface IUserDto extends IBaseDto {

  /**
   * Gets the user's username
   */
  String getUsername();

  /**
   * Gets the user's password
   */
  byte[] getPasswordHash();

  /**
   * Gets the user's password hash
   */
  byte[] getPasswordSalt();

  /**
   * Checks the given password matches the user's current password
   *
   * @param password A password
   * @return true: The password matches
   */
  boolean isPassword(String password);

  /**
   * Gets the user's last name
   */
  String getLastName();

  /**
   * Gets the user's first name
   */
  String getFirstName();

  /**
   * Gets the user's email
   */
  String getEmail();

  /**
   * Gets the user's register date
   */
  LocalDateTime getRegisterDate();

  /**
   * Gets the user's role within the application. The role is used to define the list of actions the
   * user can do.
   */
  IUserDto.Role getRole();

  /**
   * Defines the list of roles an user can hold
   */
  public static enum Role {
    USER, ADMIN
  }
}
