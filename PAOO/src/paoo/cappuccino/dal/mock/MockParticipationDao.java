package paoo.cappuccino.dal.mock;

import java.util.ArrayList;
import java.util.List;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IParticipationDao;

public class MockParticipationDao implements IParticipationDao {
  private List<IParticipation> participationList = new ArrayList<>();
  private final IEntityFactory factory;

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
    IParticipation participationEntity =
        factory.createParticipation(participation.getCompany(), participation.getBusinessDay(),
            participation.isCancelled(), 1, participation.getState());
    participationList.add(participationEntity);
    return participationEntity;
  }

  @Override
  public IParticipationDto[] fetchParticipationsByDate(int businessDayId) {
    List<IParticipation> toReturn = new ArrayList<>();
    for (IParticipation iParticipation : participationList) {
      if (iParticipation.getBusinessDay() == businessDayId)
        toReturn.add(iParticipation);
    }
    return toReturn.toArray(new IParticipation[toReturn.size()]);
  }

  @Override
  public IParticipationDto[] fetchParticipationsByCompany(int companyId) {
    List<IParticipation> toReturn = new ArrayList<>();
    for (IParticipation iParticipation : participationList) {
      if (iParticipation.getCompany() == companyId)
        toReturn.add(iParticipation);
    }
    return toReturn.toArray(new IParticipation[toReturn.size()]);
  }

}
