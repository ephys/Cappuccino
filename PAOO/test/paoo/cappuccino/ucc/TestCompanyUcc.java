package paoo.cappuccino.ucc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

/**
 * Company UCC Unit Test.
 *
 * @author Laurent
 */
public class TestCompanyUcc {

  private static DependencyInjector injector;

  private static final String name = "CrockerCorp";
  private static final String street = "High street avenue";
  private static final String num = "221b";
  private static final String boxnum = "52";
  private static final String postcode = "1234";
  private static final String town = "Bruxelles";

  private IUserDto companyCreator;

  @Inject
  private ICompanyUcc companyUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("CompanyUccTest", "0.1.1", "test"));
    injector = main.getInjector();
  }

  @Before
  public void before() throws Exception {
    injector.populate(this);

    companyCreator =
        factory.createUser(5, 1, "john", null, "lastname", "firstname", "email@test.fr",
            IUserDto.Role.ADMIN, LocalDateTime.now());
  }

  // ====================== CREATE

  @Test
  public void testCreateCompanyCorrect() {
    ICompanyDto company =
        companyUcc.create(companyCreator, name, street, num, boxnum, postcode, town);

    assertNotNull("companyUcc.create cannot return null", company);

    assertEquals("Creator match failed", company.getCreator(), companyCreator.getId());
    assertEquals("Name match failed", company.getName(), name);
    assertEquals("Street match failed", company.getAddressStreet(), street);
    assertEquals("Num match failed", company.getAddressNum(), num);
    assertEquals("MailBox match failed", company.getAddressMailbox(), boxnum);
    assertEquals("PostCode match failed", company.getAddressPostcode(), postcode);
    assertEquals("Town match failed", company.getAddressTown(), town);
  }

  @Test()
  public void testCreateCompanytCorrectEmptyMailbox() {
    ICompanyDto company =
        companyUcc.create(companyCreator, name + "2", street, num, "", postcode, town);

    assertNull(company.getAddressMailbox());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanytDupeName() {
    companyUcc.create(companyCreator, name, street, num, boxnum, postcode, town);
    companyUcc.create(companyCreator, name, street, num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyCreatorNull() {
    companyUcc.create(null, name, street, num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyNameEmpty() {
    companyUcc.create(companyCreator, "", street, num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyStreetEmpty() {
    companyUcc.create(companyCreator, name, "", num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyAddressNumEmpty() {
    companyUcc.create(companyCreator, name, street, "", boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyPostCodeEmpty() {
    companyUcc.create(companyCreator, name, street, num, boxnum, "", town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyTownEmpty() {
    companyUcc.create(companyCreator, name, street, num, boxnum, postcode, "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyNameNull() {
    companyUcc.create(companyCreator, null, street, num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyStreetNull() {
    companyUcc.create(companyCreator, name, null, num, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyAddressNumNull() {
    companyUcc.create(companyCreator, name, street, null, boxnum, postcode, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyPostCodeNull() {
    companyUcc.create(companyCreator, name, street, num, boxnum, null, town);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyTownNull() {
    companyUcc.create(companyCreator, name, street, num, boxnum, postcode, null);
  }

  // ====================== searchCompanies

  @Test
  public void testSearchCompanyOk() {
    assertNotNull(companyUcc.searchCompanies(name, postcode, town, street));
  }

  @Test
  public void testSearchCompanyNameNull() {
    assertNotNull(companyUcc.searchCompanies(null, postcode, town, street));
  }

  @Test
  public void testSearchCompanyPostCodeNull() {
    assertNotNull(companyUcc.searchCompanies(name, null, town, street));
  }

  @Test
  public void testSearchCompanyTownNull() {
    assertNotNull(companyUcc.searchCompanies(name, postcode, null, street));
  }

  @Test
  public void testSearchCompanyStreetNull() {
    assertNotNull(companyUcc.searchCompanies(name, postcode, town, null));
  }

  // ====================== getInvitableCompanies

  @Test
  public void testGetInvitableCompanies() {
    assertNotNull(companyUcc.getInvitableCompanies());
  }

  // ====================== getAllCompanies

  @Test
  public void testGetAllCompanies() {
    assertNotNull(companyUcc.getAllCompanies());
  }

  // ====================== getCompanyById

  @Test
  public void testGetCompanyById() {
    companyUcc.create(companyCreator, name + "3", street, num, boxnum, postcode, town);
    assertNotNull(companyUcc.getCompanyById(1));
  }


  @Test(expected = IllegalArgumentException.class)
  public void TestGetCompanyByIdNeg() {
    companyUcc.create(companyCreator, name + "4", street, num, boxnum, postcode, town);
    companyUcc.getCompanyById(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetCompanyByIdNull() {
    companyUcc.create(companyCreator, name + "5", street, num, boxnum, postcode, town);
    companyUcc.getCompanyById(0);
  }

  // ====================== getCompanyByDay

  @Test()
  public void TestGetCompanyByDay() {
    assertNotNull(companyUcc.getCompaniesByDay(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetCompanyByDayNull() {
    companyUcc.getCompaniesByDay(0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetCompanyByIdInvalid() {
    assertNotNull(companyUcc.getCompanyById(-1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCompanyByDayInvalid() {
    assertNotNull(companyUcc.getCompaniesByDay(-1));
  }
}
