package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * IEntityFactory implementation
 *
 * @author Nicolas Fischer
 */
final class EntityFactory implements IEntityFactory {

  @Override
  public IUser createUser(String username, String password, String lastName,
                          String firstName, String email, IUserDto.Role role) {

    return new User(username, password, lastName, firstName, email, role);
  }

  @Override
  public IUser createUser(int id, int version, String username, IHashHolderDto password,
                          String lastName, String firstName, String email, IUserDto.Role role,
                          LocalDateTime registerDate) {

    return new User(id, version, username, password, lastName, firstName, email, registerDate,
                    role);
  }

}