package paoo.cappuccino.business.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

public class TestParticipationEntity {

  @Inject
  private IEntityFactory entityFactory;

  private static DependencyInjector injector;
  private final int companyId = 1;
  private final int businessDayId = 1;
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
    participation.setState(State.CANCELLED);
    assertEquals(State.CANCELLED, participation.getState());
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
    participation.setState(State.CANCELLED);
    assertEquals(State.CANCELLED, participation.getState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetState8() {
    participation.setState(State.BILLED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetState9() {
    participation.setState(State.CANCELLED);
  }

  @Test(expected = IllegalArgumentException.class)
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

}
