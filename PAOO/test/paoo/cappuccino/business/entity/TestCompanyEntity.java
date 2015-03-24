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
  int creatorId = 1;
  String name = "Coca-Cola";
  String addressStreet = "rue du coca";
  String addressNum = "5";
  String addressMailbox = "b";
  String addressMailboxNull = null;
  String addressPostcode = "1020";
  String addressTown = "Ville de la boisson";
  ICompany company;
  ICompany company2;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ContactEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createCompany() throws Exception {
    injector.populate(this);

    company =
        entityFactory.createCompany(creatorId, name, addressStreet, addressNum, addressMailbox,
            addressPostcode, addressTown);
    company2 =
        entityFactory.createCompany(creatorId, name, addressStreet, addressNum, addressMailboxNull,
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
  public void testGetAddressMailbox2() {
    assertEquals(addressMailboxNull, company2.getAddressMailbox());
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
