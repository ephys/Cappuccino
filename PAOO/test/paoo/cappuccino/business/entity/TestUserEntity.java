package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  @Inject
  private IStringHasher hasher;

  private String username = "Nicolas";
  private char[] password = "pomme".toCharArray();
  private String lastName = "Fischer";
  private String firstName = "Benoit";
  private String email = "nicolas@gmail.com";
  private IUserDto.Role role = IUserDto.Role.USER;
  private IUser user;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("UserEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createUser() throws Exception {
    injector.populate(this);

    user = entityFactory.createUser(username, hasher.hash(password), lastName, firstName, email,
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
  public void testVersionIncrementation() {
    int version = user.getVersion();
    int incrementedVersion = user.incrementVersion();

    assertEquals(
        "user.incrementVersion() (" + incrementedVersion + ") dit not match the older version ("
        + version + ") + 1", version + 1, incrementedVersion);
    assertEquals("user.getVersion() did not match the value returned by user.incrementVersion()",
                 user.getVersion(), incrementedVersion);
  }

  @Test
  public void testRoleEnum() {
    // the hell ?

    assertEquals("There somehow is more than 2 values in enum IUserDto.Role",
                 IUserDto.Role.values().length, 2);

    assertEquals("IUserDto.Role.valueOf did not return the right value (ADMIN)",
                 IUserDto.Role.ADMIN, IUserDto.Role.valueOf("ADMIN"));
    assertEquals("IUserDto.Role.valueOf did not return the right value (USER)",
                 IUserDto.Role.USER, IUserDto.Role.valueOf("USER"));
  }
}
