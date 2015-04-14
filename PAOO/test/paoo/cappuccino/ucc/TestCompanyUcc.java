package paoo.cappuccino.ucc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
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

  private String filled = "full";
  private IUserDto user = null;
  private char[] tableauChar = new char[] {'a', 'b', 'c'};

  @Inject
  private ICompanyUcc companyUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("CompanyUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void before() throws Exception {
    injector.populate(this);
    user = factory.createUser(filled, tableauChar, filled, filled, filled, IUserDto.Role.USER);
  }

  // ====================== CREATE

  @Test()
  public void testCreateCompanyCorrect() {
    companyUcc.create(user, filled, filled, filled, filled, filled, filled);
  }

  @Test()
  public void testCreateCompanytCorrectWithMailEmpty() {
    companyUcc.create(user, filled, filled, filled, "", filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyCreatorNull() {
    companyUcc.create(null, filled, filled, filled, filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyNameEmpty() {
    companyUcc.create(user, "", filled, filled, filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyStreetEmpty() {
    companyUcc.create(user, filled, "", filled, filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyAdresseEmpty() {
    companyUcc.create(user, filled, filled, "", filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyPostCodeEmpty() {
    companyUcc.create(user, filled, filled, filled, filled, "", filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyTownEmpty() {
    companyUcc.create(user, filled, filled, filled, filled, filled, "");
  }



  // ====================== searchCompanies

  @Test()
  public void testSearchCompanyOk() {
    companyUcc.searchCompanies(filled, filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyNameNull() {
    companyUcc.searchCompanies(null, filled, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyPostCodeNull() {
    companyUcc.searchCompanies(filled, null, filled, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyTownNull() {
    companyUcc.searchCompanies(filled, filled, null, filled);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyStreetNull() {
    companyUcc.searchCompanies(filled, filled, filled, null);
  }


  // ====================== getInvitableCompanies



  // ====================== getAllCompanies


  // ====================== getCompanyById
}
