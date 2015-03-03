package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.config.injector.Inject;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;

/**
 * @author Guylian Cox
 */
class UserUcc implements IUserUcc {

  @Inject
  private IEntityFactory entityFactory;

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

    // TODO: DAL

    return entityFactory.createUser(username, password, firstName, lastName, email,
                                    IUserDto.Role.USER);
  }

  @Override
  public IUserDto logIn(String username, String password) {
    username = username.trim();
    ValidationUtil.ensureFilled(username, "username");
    ValidationUtil.ensureFilled(password, "password");
    /*
     * TODO 1. Get user entity matching username from the Data Access Layer 2. validate password on
     * that entity. 3. return the entity;
     */

    return null;
  }
}
