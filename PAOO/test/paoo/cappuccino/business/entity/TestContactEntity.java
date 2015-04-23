package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO: test contact entity

public class TestContactEntity {

  private final static int idCompany = 1;
  private final static String email = "George@gmail.com";
  private final static String firstName = "Nicolas";
  private final static String lastName = "Fischer";
  private final static String phone = "0478878594";

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  private IContact contact;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createContact() {
    injector.populate(this);

    contact = entityFactory.createContact(idCompany, email, firstName, lastName, phone);
  }

  @Test
  public void testGetCompany() {
    assertEquals(idCompany, contact.getCompany());
  }

  @Test
  public void testGetEmail() {
    assertEquals(email, contact.getEmail());
  }

  @Test
  public void testSetEmail() {
    contact.setEmail("test@gmail.com");
    assertEquals("test@gmail.com", contact.getEmail());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetEmailInvalid() {
    contact.setEmail("testgmail.com");
  }

  @Test
  public void testIsEmailValid() {
    assertTrue(contact.isEmailValid());
  }

  @Test
  public void testSetEmailValid() {
    contact.setEmailValid(false);
    assertFalse(contact.isEmailValid());
  }

  @Test
  public void testEmailReset() {
    contact.setEmailValid(false);
    contact.setEmail("pomme@john.net");
    assertTrue("An email should be marked as valid when changed", contact.isEmailValid());
  }

  @Test
  public void testGetFirstName() {
    assertEquals(firstName, contact.getFirstName());
  }

  @Test
  public void testGetLastName() {
    assertEquals(lastName, contact.getLastName());
  }

  @Test
  public void testGetPhone() {
    assertEquals(phone, contact.getPhone());
  }

  @Test
  public void testSetPhone() {
    contact.setPhone("0499999999");
    assertEquals("0499999999", contact.getPhone());
  }

  @Test
  public void testSetPhoneNull() {
    contact.setPhone(null);
    assertEquals(null, contact.getPhone());
  }

  @Test
  public void testSetEmailNull() {
    contact.setEmail(null);
    assertEquals(null, contact.getEmail());
  }

  @Test
  public void testHashCode() {
    assertEquals(makeMockEntity().hashCode(), contact.hashCode());
  }

  @Test
  public void testEquals() {
    assertTrue(makeMockEntity().equals(contact));
  }

  private IContact makeMockEntity() {
    return entityFactory.createContact(contact.getId(), 1, 5, "email", false, "de", "sa", "desf");
  }
}
