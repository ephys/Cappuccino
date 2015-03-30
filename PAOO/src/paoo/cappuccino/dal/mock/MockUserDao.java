package paoo.cappuccino.dal.mock;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IUserDao;

class MockUserDao implements IUserDao {

  private HashMap<String, IUser> users = new HashMap<>();

  private IEntityFactory factory;

  @Inject
  public MockUserDao(IEntityFactory factory) {
    this.factory = factory;
  }

  @Override
  public IUser createUser(IUserDto user) {
    IUser userEntity =
        factory.createUser(users.size() + 1, 1, user.getUsername(), user.getPassword(),
                           user.getLastName(), user.getFirstName(), user.getEmail(), user.getRole(),
                           user.getRegisterDate());

    users.put(user.getUsername().toLowerCase(), userEntity);
    return userEntity;
  }

  @Override
  public IUser fetchUserByUsername(String username) {
    return users.get(username.toLowerCase());
  }

  @Override
  public void updateUser(IUserDto user) {
    if (users.size() > user.getId()
        || users.get(user.getUsername().toLowerCase()).getVersion() != user.getVersion()) {
      throw new ConcurrentModificationException();
    }

    IUser userEntity =
        factory.createUser(user.getId(), user.getVersion() + 1, user.getUsername(),
                           user.getPassword(), user.getLastName(), user.getFirstName(),
                           user.getEmail(),
                           user.getRole(), user.getRegisterDate());

    users.replace(user.getUsername().toLowerCase(), userEntity);
  }

  @Override
  public IUser getUserById(int id) {
    for (IUser user : users.values()) {
      if (user.getId() == id) {
        return user;
      }
    }

    return null;
  }
}
