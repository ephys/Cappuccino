package paoo.cappuccino.ucc;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

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

  // ====================== changeState



  // ====================== cancelParticipation


  // ====================== getInvitationlessDays

  // ====================== getBusinessDays

  // ====================== getParticipations
}
