package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao dao;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
      IBusinessDayDao businessDayDao) {
    factory = entityFactory;
    dao = businessDayDao;
  }

  @Override
  public IBusinessDayDto create(LocalDateTime eventDate) {
    ValidationUtil.ensureNotNull(eventDate, "evenDate");

    IBusinessDayDto dto = factory.createBusinessDay(eventDate);
    // if (dao.fetchBusinessDaysByDate(eventDate.getYear()) != null) {
    // // note: fetchBusinessDaysByDate works using the academic year, not the date year
    // throw new IllegalArgumentException("A business day already exists for this year");
    // }

    try {
      return dao.createBusinessDay(dto);
    } catch (NonUniqueFieldException e) {
      throw new IllegalArgumentException("A business day already exists for this year", e);
    }
  }

  @Override
  public ICompanyDto[] addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean changeState(IParticipationDto participation, State state) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IBusinessDayDto[] getInvitationlessDays() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IBusinessDayDto[] getBusinessDays() {
    return dao.fetchAll();
  }

  @Override
  public ICompanyDto[] getAttendingCompanies(int businessDayId) {
    // TODO Auto-generated method stub
    return null;
  }
}
