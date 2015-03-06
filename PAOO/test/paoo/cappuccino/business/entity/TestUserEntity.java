package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.Main;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the User entity.
 */
public class TestUserEntity {
  //TODO: do the same for the second factory method

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;

  private String username;
  private String password;
  private String lastName;
  private String firstName;
  private String email;
  private IUserDto.Role role;
  private IUser user;

  @BeforeClass
  public static void systemInit() {
    injector = Main.configureApp(new AppContext("UserEntityTest", "0.0.1", "test"));
  }

  @Before
  public void createUser() throws Exception {
    injector.populate(this);

    this.username = "Nicolas";
    this.password = "pomme";
    this.lastName = "Fischer";
    this.firstName = "Benoit";
    this.email = "nicolas@gmail.com";
    this.role = IUserDto.Role.USER;

    user = entityFactory.createUser(username, password, lastName, firstName, email,
                                    role);
  }

  @Test
  public void testRegisterDate() {
    assertNotNull(user.getRegisterDate());
  }

  @Test
  public void testUserId() {
    assertEquals(-1, user.getId());
  }

  @Test
  public void testEmail() {
    assertEquals(email, user.getEmail());
  }

  @Test
  public void testLastName() {
    assertEquals(lastName, user.getLastName());
  }

  @Test
  public void testFirstName() {
    assertEquals(firstName, user.getFirstName());
  }

  @Test
  public void testPassword() {
    assertTrue(user.isPassword(password));
    assertFalse(user.isPassword("pomme de pain au chocococolat"));
  }

  @Test
  public void testRole() {
    assertEquals(role, user.getRole());
  }

  @Test
  public void testUsername() {
    assertEquals(username, user.getUsername());
  }
}
