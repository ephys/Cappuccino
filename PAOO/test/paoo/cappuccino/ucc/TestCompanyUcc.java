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

  private String full = "full";
  private IUserDto dto = null;
  private String emptyString = "";
  private char[] tableauChar = new char[] {'a', 'b', 'c'};


  @Inject
  private ICompanyUcc companyUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void before() throws Exception {
    injector.populate(this);
    dto = factory.createUser(full, tableauChar, full, full, full, IUserDto.Role.USER);
  }

  // ====================== CREATE

  @Test()
  public void testCreateCompanytCorrect() {
    companyUcc.create(dto, full, full, full, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyCreatorNull() {
    companyUcc.create(null, full, full, full, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyNameEmpty() {
    companyUcc.create(dto, emptyString, full, full, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyStreetEmpty() {
    companyUcc.create(dto, full, emptyString, full, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyAdresseEmpty() {
    companyUcc.create(dto, full, full, emptyString, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyPostCodeEmpty() {
    companyUcc.create(dto, full, full, full, full, emptyString, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCompanyTownEmpty() {
    companyUcc.create(dto, full, full, full, full, full, emptyString);
  }



  // ====================== searchCompanies

  @Test()
  public void testSearchCompanyOk() {
    companyUcc.searchCompanies(full, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyNameNull() {
    companyUcc.searchCompanies(null, full, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyPostCodeNull() {
    companyUcc.searchCompanies(full, null, full, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyTownNull() {
    companyUcc.searchCompanies(full, full, null, full);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSearchCompanyStreetNull() {
    companyUcc.searchCompanies(full, full, full, null);
  }


  // ====================== getInvitableCompanies



  // ====================== getAllCompanies


  // ====================== getCompanyById
}
