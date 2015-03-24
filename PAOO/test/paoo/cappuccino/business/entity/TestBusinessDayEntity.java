package paoo.cappuccino.business.entity;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

public class TestBusinessDayEntity {
  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  private LocalDateTime eventDate = LocalDateTime.of(2015, 5, 15, 15, 30);
  private IBusinessDay businessDay;

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("UserEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createBusinessDay() throws Exception {
    injector.populate(this);

    businessDay = entityFactory.createBusinessDay(eventDate);
  }



  @Test
  public void testGetEventDate() {
    assertEquals(eventDate, businessDay.getEventDate());
  }

  @Test
  public void testGetCreationDate() {
    assertNotNull(businessDay.getCreationDate());
  }
}
