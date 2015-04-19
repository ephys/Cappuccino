package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit tests for the User entity.
 */
public class TestUserEntity {

  private final static String username = "Nicolas";
  private final static char[] password = "pomme".toCharArray();
  private final static String lastName = "Fischer";
  private final static String firstName = "Benoit";
  private final static String email = "nicolas@gmail.com";
  private final static IUserDto.Role role = IUserDto.Role.USER;

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  @Inject
  private IStringHasher hasher;

  private IUser user;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("UserEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createUser() {
    injector.populate(this);

    user = entityFactory.createUser(username, password, lastName, firstName, email, role);
  }

  @Test
  public void testRegisterDate() {
    assertNotNull(user.getRegisterDate());
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
  public void testPasswordGetter() {
    assertTrue(hasher.matchHash(password, user.getPassword()));
    assertFalse(hasher.matchHash("pain".toCharArray(), user.getPassword()));
  }

  @Test
  public void testPasswordSetter() {
    IHashHolderDto newPassword = hasher.hash("chocolatine".toCharArray());
    user.setPassword(newPassword);

    assertEquals("user.getPassword() did not return the password set by user.setPassword()",
                 newPassword, user.getPassword());
  }

  @Test
  public void testRole() {
    assertEquals(role, user.getRole());
  }

  @Test
  public void testUsername() {
    assertEquals(username, user.getUsername());
  }

  @Test
  public void testRoleEnum() {
    // the hell ?

    assertEquals("There somehow is more than 2 values in enum IUserDto.Role",
                 IUserDto.Role.values().length, 2);

    assertEquals("IUserDto.Role.valueOf did not return the right value (ADMIN)",
                 IUserDto.Role.ADMIN, IUserDto.Role.valueOf("ADMIN"));
    assertEquals("IUserDto.Role.valueOf did not return the right value (USER)", IUserDto.Role.USER,
                 IUserDto.Role.valueOf("USER"));
  }

  @Test
  public void testHashCode() {
    assertEquals(makeMockEntity().hashCode(), user.hashCode());
  }

  @Test
  public void testEquals() {
    assertTrue(makeMockEntity().equals(user));
  }

  private IUser makeMockEntity() {
    return entityFactory.createUser(user.getId(), 7, "fehiug", null, "dezugi", "dehzio", "éhigh",
                                    IUserDto.Role.ADMIN, LocalDateTime.now());
  }
}
