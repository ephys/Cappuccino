package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
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
import paoo.cappuccino.dal.dao.IContactDao;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao businessDayDao;
  private final IParticipationDao participationDao;
  private final IAttendanceDao attendanceDao;
  private final IContactDao contactDao;
  private final IDalService dalService;
  private final Logger logger;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
      IBusinessDayDao businessDayDao, IParticipationDao participationDao,
      IAttendanceDao attendanceDao, AppContext app, IContactDao contactDao) {
    this.factory = entityFactory;
    this.dalService = dalService;
    this.businessDayDao = businessDayDao;
    this.participationDao = participationDao;
    this.attendanceDao = attendanceDao;
    this.contactDao = contactDao;
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
  public void addInvitedContacts(List<Integer> contacts, IBusinessDayDto businessDay,
      ICompanyDto company) {
    ValidationUtil.ensureNotNull(businessDay, "businessDay");
    ValidationUtil.ensureNotNull(contacts, "contacts");

    List<IAttendanceDto> toUpdate = new ArrayList<IAttendanceDto>();
    List<IAttendanceDto> toCancel = new ArrayList<IAttendanceDto>();
    List<IAttendanceDto> attendanceRegistred =
        attendanceDao.fetchAttendances(company.getId(), businessDay.getId());
    for (IAttendanceDto attendance : attendanceRegistred) {
      if (contacts.contains(attendance.getContact())) {
        if (attendance.isCancelled()) {
          toUpdate.add(attendance);
        }
        contacts.remove((Integer) attendance.getContact());
      } else {
        toCancel.add(attendance);
      }
    }

    dalService.startTransaction();

    for (IAttendanceDto att : toCancel) {
      IAttendance attendance = convertAttendanceDto(att);
      attendance.setCancelled(Boolean.TRUE);
      attendanceDao.updateAttendance(attendance);
    }

    for (int contact : contacts) {
      attendanceDao.createAttendance(factory.createAttendance(company.getId(), businessDay.getId(),
          contact));
    }
    for (IAttendanceDto att : toUpdate) {
      IAttendance attendance = convertAttendanceDto(att);
      attendance.setCancelled(Boolean.FALSE);
      attendanceDao.updateAttendance(attendance);
    }


    dalService.commit();
  }


  @Override
  public List<IContactDto> getInvitedContacts(ICompanyDto company, IBusinessDayDto businessDay) {
    List<IAttendanceDto> attendances =
        attendanceDao.fetchAttendances(company.getId(), businessDay.getId());

    List<IContactDto> contacts = new ArrayList<>(attendances.size());
    for (IAttendanceDto attendance : attendances) {
      contacts.add(contactDao.fetchContactById(attendance.getContact()));
    }

    return contacts;
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

  private IAttendance convertAttendanceDto(IAttendanceDto att) {
    if (att instanceof IAttendance) {
      return (IAttendance) att;
    } else {
      return factory.createAttendance(att.getCompany(), att.getBusinessDay(), att.getContact(),
          att.isCancelled(), att.getVersion());
    }


  }

  @Override
  public IBusinessDayDto getBusinessDay(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("L'id doit être positif");
    }
    return businessDayDao.fetchBusinessDayById(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see paoo.cappuccino.ucc.IBusinessDayUcc#getAttendanceForParticipation(int, int)
   */
  @Override
  public List<IAttendanceDto> getAttendanceForParticipation(int businessDay, int company) {
    if (businessDay <= 0 || company <= 0) {
      throw new IllegalArgumentException("l'id doit être positif");
    }
    return attendanceDao.fetchAttendances(company, businessDay);
  }
}
