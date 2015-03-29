package paoo.cappuccino.ucc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

/**
 * Contact UCC Unit Test.
 * 
 * @author Laurent
 */
public class TestContactUcc {

  private static DependencyInjector injector;

  private int companyId = 1;
  private String emailCorrect = "thisis@email.com";
  private String emailIncorrect = "fail.mail@oups";
  private String firstName = "FirstName";
  private String lastName = "LastName";
  private String phone = "00/000 00 00";
  private IContactDto dto = null;
  private String emptyString = "";



  @Inject
  private IContactUcc contactUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void inject() {
    injector.populate(this);
    dto = factory.createContact(1, emailCorrect, firstName, lastName, phone);
  }

  // ====================== CREATE

  @Test()
  public void testCreateContactCorrect() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactFistNameNull() {
    contactUcc.create(companyId, emailCorrect, null, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactLastNameNull() {
    contactUcc.create(companyId, emailCorrect, firstName, null, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactMailNull() {
    contactUcc.create(companyId, null, firstName, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactPhoneNull() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, null);
  }

  @Test()
  public void testCreateContactCorrectWithoutPhone() {
    contactUcc.create(companyId, emailCorrect, firstName, lastName, emptyString);
  }

  @Test()
  public void testCreateContactCorrectWithoutMail() {
    contactUcc.create(companyId, emptyString, firstName, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactFistNameIncorrect() {
    contactUcc.create(companyId, emailCorrect, emptyString, lastName, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactLastNameIncorrect() {
    contactUcc.create(companyId, emailCorrect, firstName, emptyString, phone);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateContactMailIncorrect() {
    contactUcc.create(companyId, emailIncorrect, firstName, lastName, phone);
  }

  // ====================== SETMAILVALID

  // @Test()
  // public void testSetMailInvalidReturn() {
  // assertTrue(contactUcc.setMailInvalid(dto));
  // IContactDto cdto = factory.createContact(2, emptyString, firstName, lastName, phone);
  // assertFalse(contactUcc.setMailInvalid(cdto));
  // contactUcc.setMailInvalid(dto);
  // assertFalse(contactUcc.setMailInvalid(dto));
  // }

  // ====================== SEARCHCONTACT

  @Test()
  public void testSearchContactCorrect() {
    contactUcc.searchContact(firstName, lastName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchContactFirstNameNull() {
    contactUcc.searchContact(null, lastName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchContactLastNameNull() {
    contactUcc.searchContact(firstName, null);
  }
}
