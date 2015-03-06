package paoo.cappuccino.ucc.mock;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ucc.IUserUcc;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * Mock implementation of the User use case controller
 *
 * @author Guylian Cox
 */
class MockUserUcc implements IUserUcc {

  @Inject
  private IEntityFactory factory;
  @Inject
  private IStringHasher hasher;

  @Override
  public IUserDto register(String username, String password, String firstName, String lastName,
                           String email) {
    return factory.createUser(1, 1, username, hasher.hash(password), lastName, firstName, email,
                              IUserDto.Role.USER, LocalDateTime.now());
  }

  @Override
  public IUserDto logIn(String username, String password) {
    if (!username.equalsIgnoreCase("rose") || !password.equals("doomsday")) {
      return null;
    }

    return factory.createUser(1, 1, username, hasher.hash(password), "Smith", "John",
                              "Verne@council.gal", IUserDto.Role.USER,
                              LocalDateTime.of(1963, 12, 14, 0, 0, 0));
  }
}
