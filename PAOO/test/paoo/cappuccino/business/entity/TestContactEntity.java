package paoo.cappuccino.business.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

public class TestContactEntity {
  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;


  private int idCompany = 1;
  private String email = "George@gmail.com";
  private String firstName = "Nicolas";
  private String lastName = "Fischer";
  private String phone = "0478878594";
  private IContact contact;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createContact() throws Exception {
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
  public void testSetEmail2() {
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
    assertEquals("0499999999", contact.getPhone());;
  }

}
