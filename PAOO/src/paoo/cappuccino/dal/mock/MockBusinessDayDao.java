package paoo.cappuccino.dal.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IBusinessDayDao;

public class MockBusinessDayDao implements IBusinessDayDao {
  private List<IBusinessDay> businessDayList = new ArrayList<>();
  private final IEntityFactory factory;

  @Inject
  public MockBusinessDayDao(IEntityFactory factory) {
    this.factory = factory;
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2015, 5, 15, 30, 0)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2015, 5, 15, 30, 0)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2015, 5, 15, 30, 0)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2015, 5, 15, 30, 0)));

  }

  @Override
  public IBusinessDayDto createBusinessDay(IBusinessDayDto businessDay) {
    IBusinessDay businessEntity =
        factory.createBusinessDay(businessDayList.size() + 1, 1, businessDay.getEventDate(),
            businessDay.getCreationDate());
    businessDayList.add(businessEntity);
    return businessEntity;
  }

  @Override
  public IBusinessDayDto[] fetchAll() {
    return businessDayList.toArray(new IBusinessDayDto[businessDayList.size()]);
  }

  @Override
  public IBusinessDayDto[] fetchInvitationlessDays() {
    List<IBusinessDay> toReturn = new ArrayList<>();

    for (IBusinessDay iBusinessDay : businessDayList) {
      // TODO
    }
    return toReturn.toArray(new IBusinessDayDto[toReturn.size()]);
  }

  @Override
  public IBusinessDayDto fetchBusinessDaysByDate(int year) {
    for (IBusinessDay iBusinessDay : businessDayList) {
      if (iBusinessDay.getEventDate().getYear() == year)
        return iBusinessDay;

    }
    return null;
  }
}
