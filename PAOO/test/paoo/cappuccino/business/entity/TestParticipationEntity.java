package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestParticipationEntity {

  private final static int companyId = 1;
  private final static int businessDayId = 1;

  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  private IParticipation participation;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("ParticipationEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createUser() {
    injector.populate(this);

    participation = entityFactory.createParticipation(companyId, businessDayId);
  }

  @Test
  public void testGetState() {
    assertEquals(State.INVITED, participation.getState());
  }

  @Test
  public void testGetBusinessDay() {
    assertEquals(businessDayId, participation.getBusinessDay());
  }

  @Test
  public void testGetCompany() {
    assertEquals(companyId, participation.getCompany());
  }

  // TODO demander a leconte
  // TODO rename test
  @Test
  public void testSetStateConfirmed() {
    participation.setState(State.CONFIRMED);
    assertEquals(State.CONFIRMED, participation.getState());
  }

  @Test
  public void testSetState2() {
    participation.setState(State.DECLINED);
    assertEquals(State.DECLINED, participation.getState());
  }

  @Test
  public void testSetState3() {
    participation.setState(State.CONFIRMED);
    participation.setState(State.BILLED);
    assertEquals(State.BILLED, participation.getState());
  }

  @Test
  public void testSetState5() {
    participation.setState(State.CONFIRMED);
    participation.setCancelled(true);
    assertTrue(participation.isCancelled());
  }

  @Test
  public void testSetState6() {
    participation.setState(State.CONFIRMED);
    participation.setState(State.BILLED);
    participation.setState(State.PAID);
    assertEquals(State.PAID, participation.getState());
  }

  @Test
  public void testSetState7() {
    participation.setState(State.CONFIRMED);
    participation.setState(State.BILLED);
    participation.setCancelled(true);
    assertTrue(participation.isCancelled());
  }

  @Test(expected = IllegalStateException.class)
  public void testSetState8() {
    participation.setState(State.BILLED);
  }

  @Test(expected = IllegalStateException.class)
  public void testSetState9() {
    participation.setCancelled(true);
  }

  @Test(expected = IllegalStateException.class)
  public void testSetState10() {
    participation.setState(State.PAID);
  }

  @Test
  public void testGetVersion() {
    assertEquals(0, participation.getVersion());
  }

  @Test
  public void testIncrementVersion() {
    int version = participation.getVersion();
    int incrementedVersion = participation.incrementVersion();

    assertEquals("user.incrementVersion() (" + incrementedVersion
                 + ") dit not match the older version (" + version + ") + 1", version + 1,
                 incrementedVersion);
    assertEquals("user.getVersion() did not match the value returned by user.incrementVersion()",
                 participation.getVersion(), incrementedVersion);
  }

  @Test
  public void testHashCode() {
    assertEquals(makeMockEntity().hashCode(), participation.hashCode());
  }

  @Test
  public void testEquals() {
    assertTrue(makeMockEntity().equals(participation));
  }

  private IParticipation makeMockEntity() {
    return entityFactory.createParticipation(companyId, businessDayId);
  }
}
