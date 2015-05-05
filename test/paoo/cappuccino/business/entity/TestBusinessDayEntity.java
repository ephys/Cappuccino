package paoo.cappuccino.business.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import paoo.cappuccino.BaseMain;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestBusinessDayEntity {
  private static DependencyInjector injector;

  @Inject
  private IEntityFactory entityFactory;
  private IBusinessDay businessDay;

  private final LocalDateTime eventDate = LocalDateTime.of(2015, 5, 15, 15, 30);

  @BeforeClass
  public static void systemInit() {
    BaseMain main = new BaseMain(new AppContext("BusinessDayEntityTest", "0.1.0", "test"));
    injector = main.getInjector();
  }

  @Before
  public void createBusinessDay() {
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

  @Test
  public void testHashCode() {
    IBusinessDay businessDay2 = entityFactory.createBusinessDay(businessDay.getId(),
                                                                businessDay.getVersion(),
                                                                businessDay.getEventDate(),
                                                                businessDay.getCreationDate());

    assertEquals(businessDay.hashCode(), businessDay2.hashCode());
  }

  @Test
  public void testEquals() {
    IBusinessDay businessDay2 = entityFactory.createBusinessDay(businessDay.getId(),
                                                                businessDay.getVersion(),
                                                                businessDay.getEventDate(),
                                                                businessDay.getCreationDate());

    assertTrue(businessDay.equals(businessDay2));
  }
}
