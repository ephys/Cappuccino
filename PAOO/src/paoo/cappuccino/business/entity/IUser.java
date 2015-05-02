package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IBaseDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Interface containing business methods relative to the user entity.
 *
 * @author Nicolas Fischer
 */
public interface IUser extends IUserDto, IBaseDto, IVersionedEntity {
  /**
   * Sets the user password.
   *
   * @param password A password, hashed.
   */
  void setPassword(IHashHolderDto password);

  /**
   * updates the hash algorithm used to store the password if a newer version is available.
   * @param plainPassword The user password in plain text, used to re-hash if the hash
   *                      is outdated.
   * @return The hash has been updated.
   */
  boolean updatePasswordHashAlgorithm(char[] plainPassword);
}
