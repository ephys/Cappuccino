package paoo.cappuccino.dal.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.DateUtils;

/**
 * Mock implementation of the business day dao.
 */
class MockBusinessDayDao implements IBusinessDayDao {

  private final List<IBusinessDay> businessDayList = new ArrayList<>();
  private final IEntityFactory factory;
  private final IParticipationDao participationDao;

  @Inject
  public MockBusinessDayDao(IEntityFactory factory, IParticipationDao participationDao) {
    this.factory = factory;
    this.participationDao = participationDao;

    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2015, 5, 15, 15, 30)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2016, 5, 15, 15, 30)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2014, 5, 15, 15, 30)));
    createBusinessDay(factory.createBusinessDay(LocalDateTime.of(2013, 5, 15, 15, 30)));
  }

  @Override
  public IBusinessDayDto createBusinessDay(IBusinessDayDto businessDay) {
    if (fetchBusinessDaysByDate(DateUtils.getAcademicYear(businessDay.getEventDate())) != null) {
      throw new NonUniqueFieldException("There already is a business day on that academic year.");
    }

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
    List<IBusinessDay> toReturn = businessDayList.stream()
        .filter(businessDay ->
                    participationDao.fetchParticipationsByDate(businessDay.getId()).length == 0)
        .collect(Collectors.toList());

    return toReturn.toArray(new IBusinessDayDto[toReturn.size()]);
  }

  @Override
  public IBusinessDayDto fetchBusinessDaysByDate(int year) {
    for (IBusinessDay businessDay : businessDayList) {
      if (DateUtils.getAcademicYear(businessDay.getEventDate()) == year) {
        return businessDay;
      }
    }

    return null;
  }

  @Override
  public IBusinessDayDto fetchBusinessDayById(int id) {
    return businessDayList.size() < (id - 1) ? null : businessDayList.get(id - 1);
  }
}
