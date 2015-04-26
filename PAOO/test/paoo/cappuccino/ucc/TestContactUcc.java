package paoo.cappuccino.ucc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

// TODO: contactUcc.update()

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
  private static final int companyId2 = 2;
  private static final String emailCorrect2 = "thisis2@email.com";
  private static final String firstName2 = "FirstName2";
  private static final String lastName2 = "LastName2";
  private static final String phone2 = "00/000 00 01";
  @Inject
  private IContactUcc contactUcc;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactUccTest", "0.1.1", "test"));
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



  // ====================== UPDATE --Check from here?

  @Test
  public void testUpdateContactFine() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc
            .update(contact.getId(), companyId2, emailCorrect2, firstName2, lastName2, phone2);
    assertNotNull("contactUcc.update not return null", modifiedContact);

    assertEquals("Company match failed", modifiedContact.getCompany(), companyId2);
    assertEquals("Email match failed", modifiedContact.getEmail(), emailCorrect2);
    assertEquals("First Name match failed", modifiedContact.getFirstName(), firstName2);
    assertEquals("Last Name match failed", modifiedContact.getLastName(), lastName2);
    assertEquals("Phone match failed", modifiedContact.getPhone(), phone2);
  }

  public void testUpdateContactFineCompanyNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc.update(contact.getId(), 0, emailCorrect2, firstName2, lastName2, phone2);
    assertNotNull("contactUcc.update not return null", modifiedContact);

    assertEquals("Company match failed", modifiedContact.getCompany(), companyId);
    assertEquals("Email match failed", modifiedContact.getEmail(), emailCorrect2);
    assertEquals("First Name match failed", modifiedContact.getFirstName(), firstName2);
    assertEquals("Last Name match failed", modifiedContact.getLastName(), lastName2);
    assertEquals("Phone match failed", modifiedContact.getPhone(), phone2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactFistNameNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(contact.getId(), companyId, emailCorrect, null, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactFistNameIncorrect() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(contact.getId(), companyId, emailCorrect, "", lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactLastNameNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(contact.getId(), companyId, emailCorrect, firstName, null, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactLastNameIncorrect() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(contact.getId(), companyId, emailCorrect, firstName, "", phone);
  }

  @Test
  public void testUpdateContactMailNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc.update(contact.getId(), companyId, null, firstName, lastName, phone);
    assertNull(modifiedContact.getEmail());
  }

  @Test
  public void testUpdateContactMailEmpty() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc.update(contact.getId(), companyId, "", firstName, lastName, phone);
    assertNull(modifiedContact.getEmail());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactMailIncorrect() {
    String emailIncorrect = "fail.mail@oups";
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(contact.getId(), companyId, emailIncorrect, firstName, lastName, phone);
  }

  @Test
  public void testUpdateContactPhoneNull() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc.update(contact.getId(), companyId, emailCorrect, firstName, lastName, null);
    assertNull(modifiedContact.getPhone());
  }

  @Test
  public void testUpdateContactPhoneEmpty() {
    IContactDto contact = contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    IContactDto modifiedContact =
        contactUcc.update(contact.getId(), companyId, emailCorrect, firstName, lastName, "");

    assertNull(modifiedContact.getPhone());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactIdContactNull() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(0, companyId, emailCorrect, firstName, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactIdContactNegative() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(-1, companyId, emailCorrect, firstName, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateContactIdContactNotFound() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
    contactUcc.update(999, companyId, emailCorrect, firstName, lastName, phone);
  }

  // ====================== GETCONTACTPARTICIPATIONS

  @Test
  public void testGetContactParticipations() {
    assertNotNull(contactUcc.getContactParticipations(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetContactParticipationsIdNull() {
    contactUcc.getContactParticipations(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetContactParticipationsIdNegative() {
    contactUcc.getContactParticipations(-1);
  }
}
