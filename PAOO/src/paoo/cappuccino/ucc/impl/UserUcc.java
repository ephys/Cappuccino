package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * @author Guylian Cox
 */
class UserUcc implements IUserUcc {

  @Override
  public IUserDto register(String username, String password, String firstName, String lastName,
                           String email) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public IUserDto logIn(String username, String password) {
    /* TODO
     * 1. Get user entity matching username from the Data Access Layer
     * 2. validate password on that entity.
     * 3. return the entity;
     */

    return null;
  }
}
