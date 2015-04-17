package paoo.cappuccino.ucc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Business Day UCC Unit Test.
 *
 * @author Laurent
 */
public class TestBusinessDayUcc {

  private static DependencyInjector injector;

  private String full = "full";
  private IBusinessDayDto dto = null;
  private String emptyString = "";
  private IContactDto cdto = null;
  private String emailCorrect = "mail@correct.yay";


  @Inject
  private IBusinessDayUcc businessDayUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("BusinessDayUccTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void before() throws Exception {
    injector.populate(this);
    dto = factory.createBusinessDay(LocalDateTime.now());
    cdto = factory.createContact(1, emailCorrect, full, full, full);
  }

  // ====================== CREATE

  @Test()
  public void testCreateBusinessDayCorrect() {
    businessDayUcc.create(LocalDateTime.now().plusYears(+LocalDateTime.now().getYear()));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBusinessDayIncorrect() {
    businessDayUcc.create(LocalDateTime.now());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBusinessDayTimeNull() {
    businessDayUcc.create(null);
  }


  // ====================== addInvitedCompanies

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedCompaniesCompaniesNull() {
    businessDayUcc.addInvitedCompanies(null, dto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedCompaniesDtoNull() {
    ICompanyDto[] table = {};
    businessDayUcc.addInvitedCompanies(table, null);
  }

  // ====================== changeState

  @Test(expected = ConcurrentModificationException.class)
  public void TestChangeState() {
    IParticipation participation = factory.createParticipation(1, 1);
    assertTrue(businessDayUcc.changeState(participation, State.CONFIRMED));
    assertTrue(businessDayUcc.changeState(participation, State.BILLED));

  }

  @Test()
  public void TestChangeState1() {
    IParticipation participation = factory.createParticipation(2, 2);
    assertFalse(businessDayUcc.changeState(participation, State.INVITED));
  }


  // ====================== cancelParticipation

  @Test(expected = ConcurrentModificationException.class)
  public void TestCancelParticipation() {
    IParticipation participation = factory.createParticipation(1, 1);
    participation.setState(State.CONFIRMED);
    assertTrue(businessDayUcc.cancelParticipation(participation));
    assertFalse(businessDayUcc.cancelParticipation(participation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestCancelParticipationNull() {
    businessDayUcc.cancelParticipation(null);
  }

  // ====================== getInvitationlessDays

  @Test()
  public void TestGetInvitationlessDays() {
    assertNotNull(businessDayUcc.getInvitationlessDays());
  }

  // ====================== getParticipations

  @Test()
  public void TestGetParticipations() {
    assertNotNull(businessDayUcc.getParticipations(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetParticipationsIdZero() {
    assertNotNull(businessDayUcc.getParticipations(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetParticipationsIdFail() {
    assertNotNull(businessDayUcc.getParticipations(-1));
  }

  // ======================getBusinessDays

  @Test()
  public void TestGetBusinessDays() {
    assertNotNull(businessDayUcc.getBusinessDays());
  }
}
