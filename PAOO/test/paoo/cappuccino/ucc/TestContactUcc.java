package paoo.cappuccino.ucc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Contact UCC Unit Test.
 *
 * @author Laurent
 */
public class TestContactUcc {

  private static DependencyInjector injector;

  private static final int companyId = 1;
  private static final String emailCorrect = "thisis@email.com";
  private static final String firstName = "FirstName";
  private static final String lastName = "LastName";
  private static final String phone = "00/000 00 00";

  @Inject
  private IContactUcc contactUcc;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void inject() {
    injector.populate(this);
  }

  // ====================== CREATE

  @Test
  public void testCreateContactFine() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    assertNotNull("contactUcc.create should not return null", contact);

    assertEquals("Company match failed", contact.getCompany(), companyId);
    assertEquals("Email match failed", contact.getEmail(), emailCorrect);
    assertEquals("First Name match failed", contact.getFirstName(), firstName);
    assertEquals("Last Name match failed", contact.getLastName(), lastName);
    assertEquals("Phone match failed", contact.getPhone(), phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactFistNameNull() {
    contactUcc.create(companyId, emailCorrect, null, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactFistNameIncorrect() {
    contactUcc.create(companyId, emailCorrect, "", lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactLastNameNull() {
    contactUcc.create(companyId, emailCorrect, firstName, null, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactLastNameIncorrect() {
    contactUcc.create(companyId, emailCorrect, firstName, "", phone);
  }

  @Test
  public void testCreateContactMailNull() {
    IContactDto contact = contactUcc.create(companyId, null, firstName, lastName, phone);

    assertNull(contact.getEmail());
  }

  @Test
  public void testCreateContactMailEmpty() {
    IContactDto contact = contactUcc.create(companyId, "", firstName, lastName, phone);

    assertNull(contact.getEmail());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactMailIncorrect() {
    String emailIncorrect = "fail.mail@oups";
    contactUcc.create(companyId, emailIncorrect, firstName, lastName, phone);
  }

  @Test
  public void testCreateContactPhoneNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, null);
    assertNull(contact.getPhone());
  }

  @Test
  public void testCreateContactPhoneEmpty() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, "");
    assertNull(contact.getPhone());
  }

  // ====================== SETMAILVALID

  @Test
  public void testSetMailAlreadyInvalid() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);

    assertTrue(contactUcc.setMailInvalid(contact));
    assertFalse("Mail cannot be set invalid twice", contactUcc.setMailInvalid(contact));
  }

  @Test
  public void testSetMailAlready() {
    IContactDto contact = contactUcc.create(companyId, null, firstName, lastName, phone);
    assertFalse(contactUcc.setMailInvalid(contact));
  }

  // ====================== SEARCHCONTACT

  @Test
  public void testSearchContactFine() {
    assertNotNull(contactUcc.searchContact(firstName, lastName));
  }

  @Test
  public void testSearchContactFirstNameNull() {
    assertNotNull(contactUcc.searchContact(null, lastName));
  }

  @Test
  public void testSearchContactLastNameNull() {
    assertNotNull(contactUcc.searchContact(firstName, null));
  }

  // ====================== getContactByCompany

  @Test
  public void testGetContactByCompanyOk() {
    assertNotNull(contactUcc.getContactByCompany(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetContactByCompanyIdNegative() {
    contactUcc.getContactByCompany(-1);
  }
}
