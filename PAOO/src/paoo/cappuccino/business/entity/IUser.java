package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IUserDto;


/**
 * Interface containing business methods relative to the user entity.
 *
 * @author Nicolas Fischer
 */
public interface IUser extends IUserDto, IBaseEntity {
  /**
   * Sets the user password.
   *
   * @param password A valid password
   */
  void setPassword(String password);

  /**
   * Upgrades the password hash algorithm.
   *
   * @return true: the password has been upgraded and the entity altered.
   * @param password The unhashed password.
   */
  boolean upgradePassword(String password);
}
