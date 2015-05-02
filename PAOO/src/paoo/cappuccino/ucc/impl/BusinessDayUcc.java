package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.IAttendance;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IAttendanceDao;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao businessDayDao;
  private final IParticipationDao participationDao;
  private final IAttendanceDao attendanceDao;
  private final IDalService dalService;
  private final Logger logger;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
                        IBusinessDayDao businessDayDao, IParticipationDao participationDao,
                        IAttendanceDao attendanceDao, AppContext app) {

    this.factory = entityFactory;
    this.dalService = dalService;
    this.businessDayDao = businessDayDao;
    this.participationDao = participationDao;
    this.attendanceDao = attendanceDao;
    this.logger = app.getLogger("BusinessDayUcc");
  }

  @Override
  public IBusinessDayDto create(LocalDateTime eventDate) {
    ValidationUtil.ensureNotNull(eventDate, "eventDate");

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
  public void setInvitedContacts(List<Integer> newAttendances, IBusinessDayDto businessDay,
                                 ICompanyDto company) {
    ValidationUtil.ensureNotNull(businessDay, "businessDay");
    ValidationUtil.ensureNotNull(newAttendances, "contacts");
    ValidationUtil.ensureNotNull(company, "company");

    List<IAttendanceDto> currentAttendances = attendanceDao.fetchAttendances(company.getId(),
                                                                             businessDay.getId());

    newAttendances = new ArrayList<>(newAttendances);

    dalService.startTransaction();

    try {
      for (IAttendanceDto currentAttendance : currentAttendances) {
        /* this contact is already in the attending list:
         * - Remove the cancellation if he cancelled
         * - Don't create a new participation for him
         */
        if (newAttendances.contains(currentAttendance.getContact())) {
          if (currentAttendance.isCancelled()) {
            IAttendance attendanceToUncancel = convertAttendanceDto(currentAttendance);
            attendanceToUncancel.setCancelled(false);
            attendanceDao.updateAttendance(attendanceToUncancel);
          }

          newAttendances.remove(currentAttendance.getContact());
        } else { // An attending contact is not in the updated list ? Cancel.
          IAttendance attendanceToCancel = convertAttendanceDto(currentAttendance);
          attendanceToCancel.setCancelled(true);
          attendanceDao.updateAttendance(attendanceToCancel);
        }
      }

      // create new attendances for the new contacts that weren't in the list before.
      for (int contact : newAttendances) {
        attendanceDao.createAttendance(factory.createAttendance(company.getId(),
                                                                businessDay.getId(),
                                                                contact));
      }
    } catch (ConcurrentModificationException e) {
      dalService.rollback();

      throw e;
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
  public IBusinessDayDto getBusinessDay(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid day id " + id);
    }

    return businessDayDao.fetchBusinessDayById(id);
  }

  @Override
  public List<IParticipationDto> getParticipations(int businessDayId) {
    if (businessDayId <= 0) {
      throw new IllegalArgumentException("Invalid day id " + businessDayId);
    }

    return participationDao.fetchParticipationsByDate(businessDayId);
  }

  @Override
  public List<IAttendanceDto> getAttendancesForParticipation(int businessDay, int company) {
    if (businessDay <= 0 || company <= 0) {
      throw new IllegalArgumentException("A given id is invalid (day: " + businessDay
                                         + ", company: " + company + ")");
    }

    return attendanceDao.fetchAttendances(company, businessDay);
  }

  private IParticipation convertParticipationDto(IParticipationDto dto) {
    if (dto instanceof IParticipation) {
      return (IParticipation) dto;
    } else {
      return factory.createParticipation(dto.getCompany(), dto.getBusinessDay(), dto.isCancelled(),
                                         dto.getVersion(), dto.getState());
    }
  }

  private IAttendance convertAttendanceDto(IAttendanceDto att) {
    if (att instanceof IAttendance) {
      return (IAttendance) att;
    } else {
      return factory.createAttendance(att.getCompany(), att.getBusinessDay(), att.getContact(),
                                      att.isCancelled(), att.getVersion());
    }
  }
}
