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

import static org.junit.Assert.assertEquals;

public class TestBaseEntity {

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;

  private IUser user;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("BaseEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createUser() {
    injector.populate(this);

    String email = "nicolas@gmail.com";
    String firstName = "Benoit";
    String username = "Nicolas";
    String lastName = "Fischer";
    char[] password = "pomme".toCharArray();
    IUserDto.Role role = IUserDto.Role.USER;
    user = entityFactory.createUser(username, password, lastName, firstName, email, role);
  }

  @Test
  public void testGetId() {
    assertEquals(-1, user.getId());
  }

  @Test
  public void testGetVersion() {
    assertEquals(0, user.getVersion());
  }

  @Test
  public void testIncrementVersion() {
    int version = user.getVersion();
    int incrementedVersion = user.incrementVersion();

    assertEquals("user.incrementVersion() (" + incrementedVersion
        + ") dit not match the older version (" + version + ") + 1", version + 1,
        incrementedVersion);
    assertEquals("user.getVersion() did not match the value returned by user.incrementVersion()",
        user.getVersion(), incrementedVersion);
  }
}
