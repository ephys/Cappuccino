package paoo.cappuccino.ucc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
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
  private static final LocalDateTime eventDate = LocalDateTime.now().plusYears(200);
  private static int nbBusinessDays = 0;

  @Inject
  private IBusinessDayUcc businessDayUcc;

  @Inject
  private IEntityFactory factory;

  @BeforeClass
  public static void systemInit() {
    BaseMain main =
        new BaseMain(new AppContext("BusinessDayUccTest", "0.2.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void before() throws Exception {
    injector.populate(this);
  }

  // ====================== CREATE

  @Test()
  public void testCreateBusinessDayCorrect() {
    IBusinessDayDto businessDay = businessDayUcc.create(eventDate);

    assertNotNull("businessDayUcc.create cannot return null.", businessDay);
    assertNotNull("Creation date did not match",
                  businessDay.getCreationDate());
    assertEquals("Creation date did not match", eventDate,
                 businessDay.getEventDate());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBusinessDayDupe() {
    businessDayUcc.create(eventDate.minusYears(1));
    businessDayUcc.create(eventDate.minusYears(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateBusinessDayTimeNull() {
    businessDayUcc.create(null);
  }

  // ====================== addInvitedCompanies

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedCompaniesCompaniesNull() {
    IBusinessDayDto day = makeBusinessDay();
    businessDayUcc.addInvitedCompanies(null, day);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedCompaniesDtoNull() {
    businessDayUcc.addInvitedCompanies(new ICompanyDto[0], null);
  }

  @Test
  public void testAddInvitedCompanies() {
    IBusinessDayDto day = makeBusinessDay();

    businessDayUcc.addInvitedCompanies(new ICompanyDto[]{makeCompany()},
                                       day);

    List<IParticipationDto> participations =
        businessDayUcc.getParticipations(day.getId());
    assertEquals(participations.size(), 1);

    assertEquals("Participation initiated with the wrong state.",
                 participations.get(0).getState(), State.INVITED);
  }

  //======================setInvitedContacts

  @Test(expected = IllegalArgumentException.class)

  public void testAddInvitedContactsListNull() {
    businessDayUcc.setInvitedContacts(null, makeBusinessDay(), makeCompany());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedContactsDayNull() {
    businessDayUcc.setInvitedContacts(new ArrayList<>(), null, makeCompany());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddInvitedContactsCompanyNull() {
    businessDayUcc.setInvitedContacts(new ArrayList<>(), makeBusinessDay(), null);
  }

  @Test
  public void testAddInvitedContacts() {
    ICompanyDto company = makeCompany();
    IBusinessDayDto day = makeBusinessDay();

    businessDayUcc.setInvitedContacts(Collections.singletonList(1), day, company);

    List<IAttendanceDto> participatingContacts = businessDayUcc.getAttendancesForParticipation(
        day.getId(), company.getId());

    assertEquals(participatingContacts.size(), 1);
  }

  // ====================== changeState

  @Test
  public void testChangeState() {
    IParticipationDto participation = makeParticipation();

    assertTrue("Transition INVITED -> CONFIRMED failled",
               businessDayUcc.changeState(participation, State.CONFIRMED));
    assertEquals("Participation state not updated in the entity",
                 participation.getState(), State.CONFIRMED);
  }

  @Test
  public void testChangeStateWrong() {
    IParticipationDto participation = makeParticipation();
    assertFalse(businessDayUcc.changeState(participation, State.INVITED));
  }

  // ====================== cancelParticipation

  @Test
  public void testCancelParticipation() {
    IParticipationDto participation = makeParticipation();

    businessDayUcc.changeState(participation, State.CONFIRMED);

    assertTrue("Cancellation denied",
               businessDayUcc.cancelParticipation(participation));
    assertFalse("Cancellation accepted when already cancelled",
                businessDayUcc.cancelParticipation(participation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCancelParticipationNull() {
    businessDayUcc.cancelParticipation(null);
  }

  // ====================== getInvitationlessDays

  @Test()
  public void testGetInvitationlessDays() {
    assertNotNull(businessDayUcc.getInvitationlessDays());
  }

  // ====================== getParticipations

  @Test()
  public void testGetParticipations() {
    assertNotNull(businessDayUcc.getParticipations(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetParticipationsIdFail() {
    assertNotNull(businessDayUcc.getParticipations(-1));
  }

  // ======================getBusinessDays

  @Test
  public void testGetBusinessDays() {
    assertNotNull(businessDayUcc.getBusinessDays());
  }

  // ======================getBusinessDaysById --Check down here

  @Test
  public void testGetBusinessDaysById() {
    assertNotNull(businessDayUcc.getBusinessDay(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetBusinessDaysByIdNull() {
    businessDayUcc.getBusinessDay(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetBusinessDaysByIdNegative() {
    businessDayUcc.getBusinessDay(-1);
  }

  // ======================utilities
  private ICompany makeCompany() {
    return factory.createCompany(1, 1, 1, "Beure de cacahuètes", "c'est",
                                 "tout", "ce", "que", "j'ai sous la main", LocalDateTime.now());
  }

  private IContact makeContact() {
    return factory.createContact(1, 1, 1, "john@blbl.net", true, "john",
                                 "bob", null);
  }

  private IBusinessDayDto makeBusinessDay() {
    return businessDayUcc.create(eventDate.plusYears(++nbBusinessDays));
  }

  private List<IParticipationDto> makeParticipation(
      IBusinessDayDto businessDay) {
    businessDayUcc.addInvitedCompanies(new ICompanyDto[]{makeCompany()},
                                       businessDay);

    return businessDayUcc.getParticipations(businessDay.getId());
  }

  private IParticipationDto makeParticipation() {
    return makeParticipation(makeBusinessDay()).get(0);
  }
}
