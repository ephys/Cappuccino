package paoo.cappuccino.dal.impl;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IParticipationDao;

/**
 * IParticipationDao implementation.
 *
 * @author Kevin Bavay
 */
public class ParticipationDao implements IParticipationDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;

  @Inject
  public ParticipationDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IParticipationDto createParticipation(IParticipationDto participation) {
    //TODO
    return null;
  }

  @Override
  public IParticipationDto[] fetchParticipationsByDate(int businessDayId) {
    //TODO
    return new IParticipationDto[0];
  }

  @Override
  public IParticipationDto[] fetchParticipationsByCompany(int companyId) {
    //TODO
    return new IParticipationDto[0];
  }
}
