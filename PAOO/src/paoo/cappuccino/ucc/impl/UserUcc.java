package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.config.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;

/**
 * @author Guylian Cox
 */
class UserUcc implements IUserUcc {

  @Inject
  private IEntityFactory entityFactory;
  @Inject
  private IDalService dalService;
  @Inject
  private IUserDao userDao;

  @Override
  public IUserDto register(String username, String password, String firstName, String lastName,
                           String email) {
    username = username.trim();
    if (username.matches(".*\\w.*")) {
      throw new IllegalArgumentException("username cannot contain any spaces");
    }

    password = password.trim();
    ValidationUtil.validatePassword(password, "password");
    ValidationUtil.ensureFilled(firstName, "firstName");
    ValidationUtil.ensureFilled(lastName, "lastName");

    ValidationUtil.ensureNotNull(email, "email");
    if (!StringUtils.isEmail(email)) {
      throw new IllegalArgumentException("Invalid email format");
    }

    IUser registeredUser = entityFactory.createUser(username, password, firstName, lastName, email,
                                                    IUserDto.Role.USER);

    dalService.startTransaction();
    registeredUser = userDao.createUser(registeredUser);
    dalService.commit();

    return registeredUser;
  }

  @Override
  public IUserDto logIn(String username, String password) {
    username = username.trim();
    ValidationUtil.ensureFilled(username, "username");

    password = password.trim();
    ValidationUtil.ensureFilled(password, "password");

    IUser user = userDao.fetchUserByUsername(username);
    if (!user.isPassword(password)) return null;

    if (user.upgradePassword(password)) {
      userDao.updateUser(user);
    }

    return user;
  }
}
