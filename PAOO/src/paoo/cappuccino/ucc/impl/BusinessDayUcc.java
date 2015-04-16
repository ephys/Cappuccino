package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao businessDayDao;
  private final IParticipationDao participationDao;
  private final IDalService dalService;
  private final Logger logger;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
      IBusinessDayDao businessDayDao, IParticipationDao participationDao, AppContext app) {
    this.factory = entityFactory;
    this.dalService = dalService;
    this.businessDayDao = businessDayDao;
    this.participationDao = participationDao;
    this.logger = app.getLogger("BusinessDayUcc");
  }

  @Override
  public IBusinessDayDto create(LocalDateTime eventDate) {
    ValidationUtil.ensureNotNull(eventDate, "evenDate");

    IBusinessDayDto dto = factory.createBusinessDay(eventDate);
    try {
      return businessDayDao.createBusinessDay(dto);
    } catch (NonUniqueFieldException e) {
      throw new IllegalArgumentException("A business day already exists for that academic year", e);
    }
  }

  @Override
  public void addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay) {
    ValidationUtil.ensureNotNull(businessDay, "businessDay");
    ValidationUtil.ensureNotNull(companies, "companies");

    dalService.startTransaction();

    for (ICompanyDto company : companies) {
      IParticipationDto participation =
          factory.createParticipation(company.getId(), businessDay.getId());

      participationDao.createParticipation(participation);
    }

    dalService.commit();
  }

  @Override
  public boolean changeState(IParticipationDto participation, State state) {
    ValidationUtil.ensureNotNull(participation, "participation");
    ValidationUtil.ensureNotNull(state, "state");
    IParticipation participationEntity = convertParticipationDto(participation);

    try {
      participationEntity.setState(state);
    } catch (IllegalStateException e) {
      logger.log(Level.INFO, "state change failed", e);
      return false;
    }

    participationDao.updateParticipation(participation);
    return true;
  }

  @Override
  public boolean cancelParticipation(IParticipationDto participationDto) {
    ValidationUtil.ensureNotNull(participationDto, "participationDto");
    if (participationDto.isCancelled()) {
      return false;
    }

    IParticipation participation = convertParticipationDto(participationDto);
    participation.setCancelled(true);
    participationDao.updateParticipation(participation);

    return true;
  }

  @Override
  public List<IBusinessDayDto> getInvitationlessDays() {
    return businessDayDao.fetchInvitationlessDays();
  }

  @Override
  public List<IBusinessDayDto> getBusinessDays() {
    return businessDayDao.fetchAll();
  }

  @Override
  public List<IParticipationDto> getParticipations(int businessDayId) {
    if (businessDayId <= 0) {
      throw new IllegalArgumentException("invalid id");
    }
    return participationDao.fetchParticipationsByDate(businessDayId);
  }

  private IParticipation convertParticipationDto(IParticipationDto dto) {
    if (dto instanceof IParticipation) {
      return (IParticipation) dto;
    } else {
      return factory.createParticipation(dto.getCompany(), dto.getBusinessDay(), dto.isCancelled(),
          dto.getVersion(), dto.getState());
    }
  }
}
