package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Interface containing business methods relative to the user entity.
 *
 * @author Nicolas Fischer
 */
public interface IUser extends IUserDto, IBaseEntity {
  /**
   * Sets the user password.
   *
   * @param password A password, hashed.
   */
  void setPassword(IHashHolderDto password);
}
