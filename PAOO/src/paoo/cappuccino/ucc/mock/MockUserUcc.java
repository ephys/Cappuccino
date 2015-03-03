package paoo.cappuccino.ucc.mock;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Mock implementation of the User use case controller
 *
 * @author Guylian Cox
 */
class MockUserUcc implements IUserUcc {

  @Override
  public IUserDto register(String username, String password, String firstName, String lastName,
                           String email) {
    return null;
  }

  @Override
  public IUserDto logIn(String username, String password) {
    return null;
  }
}
