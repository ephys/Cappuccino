package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAttendanceEntity {

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  private IAttendanceDto attendance;

  private static final int BUSINESS_DAY = 5;
  private static final int CONTACT = 12;
  private static final int COMPANY = 20;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("TestAttendanceEntity", "0.0.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createBusinessDay() {
    injector.populate(this);

    attendance = entityFactory.createAttendance(COMPANY, BUSINESS_DAY, CONTACT);
  }

  @Test
  public void testGetCompany() {
    assertEquals(COMPANY, attendance.getCompany());
  }

  @Test
  public void testGetContact() {
    assertEquals(CONTACT, attendance.getContact());
  }

  @Test
  public void testGetBusinessDay() {
    assertEquals(BUSINESS_DAY, attendance.getBusinessDay());
  }

  @Test
  public void testHashCode() {
    IAttendance attendance2 = entityFactory.createAttendance(COMPANY, BUSINESS_DAY, CONTACT);

    assertEquals(attendance.hashCode(), attendance2.hashCode());
  }

  @Test
  public void testEquals() {
    IAttendance attendance2 = entityFactory.createAttendance(COMPANY, BUSINESS_DAY, CONTACT);

    assertTrue(attendance2.equals(attendance));
  }

  @Test
  public void testEqualsCompany() {
    IAttendance attendance2 = entityFactory.createAttendance(COMPANY + 1, BUSINESS_DAY, CONTACT);

    assertFalse(attendance2.equals(attendance));
  }

  @Test
  public void testEqualsBusinessDay() {
    IAttendance attendance2 = entityFactory.createAttendance(COMPANY, BUSINESS_DAY + 1, CONTACT);

    assertFalse(attendance2.equals(attendance));
  }

  @Test
  public void testEqualsContact() {
    IAttendance attendance2 = entityFactory.createAttendance(COMPANY, BUSINESS_DAY, CONTACT + 1);

    assertFalse(attendance2.equals(attendance));
  }
}
