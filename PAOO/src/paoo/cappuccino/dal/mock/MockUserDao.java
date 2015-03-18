package paoo.cappuccino.dal.mock;

import java.util.HashMap;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IUserDao;

class MockUserDao implements IUserDao {

  private int entityCount = 0;
  private HashMap<String, IUser> users = new HashMap<>();

  private IEntityFactory factory;

  @Inject
  public MockUserDao(IEntityFactory factory) {
    this.factory = factory;
  }
  
  @Override
  public IUser createUser(IUserDto user) {
    IUser userEntity = factory.createUser(++entityCount, 1, user.getUsername(), user.getPassword(),
                                          user.getLastName(), user.getFirstName(), user.getEmail(),
                                          user.getRole(), user.getRegisterDate());

    users.put(user.getUsername().toLowerCase(), userEntity);
    return userEntity;
  }

  @Override
  public IUser fetchUserByUsername(String username) {
    return users.get(username.toLowerCase());
  }

  @Override
  public void updateUser(IUser user) {
    // do nothing
  }

  @Override
  public IUser getCompanyCreator(int company) {
    return null;
  }
}
