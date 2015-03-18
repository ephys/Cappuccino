package paoo.cappuccino.ucc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * User UCC Unit test
 */
public class TestUserUcc {

  private static DependencyInjector injector;

  private String username = "john_";
  private char[] password = "chocolat".toCharArray();
  private String firstName = "John";
  private String lastName = "Egbert";
  private String email = "johnegbert@sn.ms";

  @Inject
  private IUserUcc userUcc;
  @Inject
  private IStringHasher hasher;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("UserUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void inject() {
    injector.populate(this);
  }

  // ====================== REGISTER

  @Test
  public void testRegisterReturn() {
    IUserDto user = userUcc.register(username, password, firstName, lastName, email);

    assertNotNull("register should return a user dto", user);

    assertEquals("Email not equal", user.getEmail(), email.trim());
    assertEquals("username not equal", user.getUsername(), username.trim());
    assertTrue("password not equal", hasher.matchHash(password, user.getPassword()));
    assertEquals("first name not equal", user.getFirstName(), firstName.trim());
    assertEquals("first name not equal", user.getLastName(), lastName.trim());
    assertEquals("email not equal", user.getEmail(), email.trim());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegisterUsername() {
    userUcc.register(" jo hn", password, firstName, lastName, email);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegisterPassword() {
    userUcc.register(username, new char[]{ '1', '2', '3', '4', '5' }, firstName, lastName, email);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRegisterEmail() {
    userUcc.register(username, password, firstName, lastName, "potatoes");
  }

  @Test()
  public void testRegisterNull() {
    try {
      userUcc.register(null, password, firstName, lastName, email);

      fail("the username cannot be null");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, null, firstName, lastName, email);

      fail("the password cannot be null");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, null, lastName, email);

      fail("the first name cannot be null");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, firstName, null, email);

      fail("the last name cannot be null");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, firstName, lastName, null);

      fail("the email cannot be null");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testRegisterEmpty() {
    try {
      userUcc.register("", password, firstName, lastName, email);

      fail("The username cannot be empty");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, new char[0], firstName, lastName, email);

      fail("The password cannot be empty");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, "", lastName, email);

      fail("The first name cannot be empty");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, firstName, "", email);

      fail("The last name cannot be empty");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      userUcc.register(username, password, firstName, lastName, "");

      fail("The email cannot be empty");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  // ====================== LOGIN

  @Test
  public void testLoginOkay() {
    userUcc.register(username, password, firstName, lastName, email);

    assertNotNull("Valid login, should not return null", userUcc.logIn(username, password));
  }

  @Test
  public void testLoginCase() {
    userUcc.register(username, password, firstName, lastName, email);

    assertNotNull("Valid login, should not return null", userUcc.logIn(username.toUpperCase(), password));
  }

  @Test
  public void testLoginWrongPassword() {
    userUcc.register(username, password, firstName, lastName, email);

    assertNull("Incorrect password, should return null", userUcc.logIn(username, new char[] { 'f', 'l', 'u', 'f', 'l' }));
  }

  @Test
  public void testLoginWrongUsername() {
    userUcc.register(username, password, firstName, lastName, email);

    assertNull("Incorrect username, should return null", userUcc.logIn("john ?", password));
  }

  @Test
  public void testLoginEmpty() {
    userUcc.register(username, password, firstName, lastName, email);

    try {
      assertNull("Incorrect username, should throw IllegalArgumentException", userUcc.logIn(null, password));

      fail("Username null, should throw IllegalArgumentException.");
    } catch(IllegalArgumentException e) {
      assertTrue(true);
    }

    try {
      assertNull("Incorrect username, should throw IllegalArgumentException", userUcc.logIn(username, null));

      fail("Password null, should throw IllegalArgumentException.");
    } catch(IllegalArgumentException e) {
      assertTrue(true);
    }
  }
}