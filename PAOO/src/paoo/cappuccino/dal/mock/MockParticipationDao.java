package paoo.cappuccino.dal.mock;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IParticipationDao;

/**
 * Mock implementation of the participation dao.
 */
class MockParticipationDao implements IParticipationDao {

  private final IEntityFactory factory;
  private final List<IParticipation> participationList = new ArrayList<>();

  @Inject
  public MockParticipationDao(IEntityFactory factory) {
    this.factory = factory;
    createParticipation(factory.createParticipation(1, 1, false, 2,
                                                    IParticipationDto.State.INVITED));
    createParticipation(factory.createParticipation(2, 1, false, 2,
                                                    IParticipationDto.State.BILLED));
    createParticipation(factory.createParticipation(3, 2, false, 2,
                                                    IParticipationDto.State.BILLED));
    createParticipation(factory.createParticipation(4, 1, false, 2,
                                                    IParticipationDto.State.DECLINED));
  }

  @Override
  public IParticipationDto createParticipation(IParticipationDto participation) {
    IParticipation participationEntity = factory.createParticipation(participation.getCompany(),
                                                                     participation.getBusinessDay(),
                                                                     participation.isCancelled(), 1,
                                                                     participation.getState());

    participationList.add(participationEntity);

    return participationEntity;
  }

  @Override
  public void updateParticipation(IParticipationDto participation) {
    for (int i = 0; i < participationList.size(); i++) {
      final IParticipation localParticipation = participationList.get(i);

      if (localParticipation.getCompany() == participation.getCompany()
          && participation.getBusinessDay() == localParticipation.getBusinessDay()) {

        if (localParticipation.getVersion() != participation.getVersion()) {
          throw new ConcurrentModificationException("Participation version mismatch");
        }

        participationList.set(i, factory.createParticipation(participation.getCompany(),
                                                             participation.getBusinessDay(),
                                                             participation.isCancelled(),
                                                             participation.getVersion(),
                                                             participation.getState()));
      }
    }

    throw new ConcurrentModificationException("Participation does not exist");
  }

  @Override
  public List<IParticipationDto> fetchParticipationsByDate(int businessDayId) {
    return participationList.stream()
        .filter(participation -> participation.getBusinessDay() == businessDayId)
        .collect(Collectors.toList());
  }

  @Override
  public List<IParticipationDto> fetchParticipationsByCompany(int companyId) {
    return participationList.stream()
        .filter(participation -> participation.getCompany() == companyId)
        .collect(Collectors.toList());
  }
}
