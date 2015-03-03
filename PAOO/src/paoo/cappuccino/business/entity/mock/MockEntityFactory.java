package paoo.cappuccino.business.entity.mock;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * IEntityFactory mock implementation
 *
 * @author Nicolas Fischer
 */
final class MockEntityFactory implements IEntityFactory {
  //TODO
  
  @Override
  public IUser createUser(String username, String password, String lastName,
                          String firstName, String email, IUserDto.Role role) {
    return null;
  }

  @Override
  public IUser createUser(int id, int version, String username, IHashHolderDto password,
                          String lastName, String firstName, String email, IUserDto.Role role,
                          LocalDateTime registerDate) {
    return null;
  }
}
