package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;

/**
 * Legit implementation of the User use case controller.
 *
 * @author Guylian Cox
 */
class UserUcc implements IUserUcc {

  private final IEntityFactory entityFactory;
  private final IUserDao userDao;

  @Inject
  public UserUcc(IEntityFactory entityFactory, IDalService dalService, IUserDao userDao) {
    this.entityFactory = entityFactory;
    this.userDao = userDao;
  }

  @Override
  public IUserDto register(String username, char[] password, String firstName, String lastName,
      String email) {
    ValidationUtil.ensureFilled(username, "username");
    username = username.trim();

    if (username.matches("^.*\\s.*$")) {
      throw new IllegalArgumentException("username cannot contain any spaces");
    }

    ValidationUtil.validatePassword(password, "password");
    ValidationUtil.ensureFilled(firstName, "firstName");
    ValidationUtil.ensureFilled(lastName, "lastName");

    ValidationUtil.ensureNotNull(email, "email");
    email = email.trim();
    if (!StringUtils.isEmail(email)) {
      throw new IllegalArgumentException("Invalid email format");
    }

    firstName = firstName.trim();
    lastName = lastName.trim();
    IUser registeredUser = entityFactory.createUser(username, password, lastName,
                                                    firstName, email, IUserDto.Role.USER);

    return userDao.createUser(registeredUser);
  }

  @Override
  public IUserDto logIn(String username, char[] password) {
    ValidationUtil.ensureFilled(username, "username");
    ValidationUtil.ensureFilled(password, "password");

    IUser user = (IUser) userDao.fetchUserByUsername(username.trim());
    if (user == null || !user.isPassword(password)) {
      return null;
    }

    if (user.updatePasswordHashAlgorithm(password)) {
      userDao.updateUser(user);
    }

    return user;
  }
}
