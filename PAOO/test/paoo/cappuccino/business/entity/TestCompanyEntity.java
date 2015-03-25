package paoo.cappuccino.business.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

public class TestCompanyEntity {
  private static DependencyInjector injector;
  @Inject
  private IEntityFactory entityFactory;

  private final int creatorId = 1;
  private final String name = "Coca-Cola";
  private final String addressStreet = "rue du coca";
  private final String addressNum = "5";
  private final String addressMailbox = "b";
  private final String addressPostcode = "1020";
  private final String addressTown = "Ville de la boisson";
  private ICompany company;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createCompany() {
    injector.populate(this);

    company =
        entityFactory.createCompany(creatorId, name, addressStreet, addressNum, addressMailbox,
            addressPostcode, addressTown);

  }

  @Test
  public void testGetName() {
    assertEquals(name, company.getName());
  }


  @Test
  public void testRegisterDate() {
    assertNotNull(company.getRegisterDate());
  }


  @Test
  public void testGetAddressStreet() {
    assertEquals(addressStreet, company.getAddressStreet());
  }

  @Test
  public void testGetAddressNum() {
    assertEquals(addressNum, company.getAddressNum());
  }

  @Test
  public void testGetAddressMailbox() {
    assertEquals(addressMailbox, company.getAddressMailbox());
  }

  @Test
  public void testGetAddressMailboxNull() {
    ICompany company2 =
        entityFactory.createCompany(creatorId, name, addressStreet, addressNum, null,
            addressPostcode, addressTown);
    assertEquals(null, company2.getAddressMailbox());
  }

  @Test
  public void testGetAddressPostcode() {
    assertEquals(addressPostcode, company.getAddressPostcode());
  }

  @Test
  public void testGetAddressTown() {
    assertEquals(addressTown, company.getAddressTown());
  }

  @Test
  public void testGetCreator() {
    assertEquals(creatorId, company.getCreator());
  }

}
