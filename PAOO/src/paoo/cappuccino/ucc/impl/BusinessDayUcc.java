package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao businessDayDao;
  private final IParticipationDao participationDao;
  private final ICompanyDao companyDao;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
      IBusinessDayDao businessDayDao, IParticipationDao participationDao, ICompanyDao companyDao) {
    this.factory = entityFactory;
    this.businessDayDao = businessDayDao;
    this.participationDao = participationDao;
    this.companyDao = companyDao;
  }

  @Override
  public IBusinessDayDto create(LocalDateTime eventDate) {
    ValidationUtil.ensureNotNull(eventDate, "evenDate");

    IBusinessDayDto dto = factory.createBusinessDay(eventDate);
    try {
      return businessDayDao.createBusinessDay(dto);
    } catch (NonUniqueFieldException e) {
      throw new IllegalArgumentException("A business day already exists for this year", e);
    }
  }

  @Override
  public ICompanyDto[] addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay) {

    // check si non null
    ValidationUtil.ensureNotNull(businessDay, "businessDay");
    ValidationUtil.ensureNotNull(companies, "companies");

    // check siil y a effectivement des entreprises dans la liste à rajouter
    if (companies.length == 0) {
      throw new IllegalArgumentException("No companies found in the parameter companies");
    }

    // crée les participations et les sauve dans la db
    for (int i = 0; i < companies.length; i++) {
      participationDao.createParticipation(factory.createParticipation(companies[i].getId(),
          businessDay.getId()));
    }

    // Récupération des ids de participations de la journée visée
    IParticipationDto[] array = participationDao.fetchParticipationsByDate(businessDay.getId());

    // Création du tableau de retour
    ICompanyDto[] returnedValues = new ICompanyDto[array.length];

    // itération du tableau des participations et récupération des entreprises liées
    for (int i = 0; i < array.length; i++) {
      returnedValues[i] = companyDao.fetchCompanyById(array[i].getCompany());
    }

    return returnedValues;
  }

  @Override
  public boolean changeState(IParticipationDto participation, State state) {
    IParticipation entity = (IParticipation) participation;
    entity.setState(state);
    return false;
  }

  @Override
  public boolean cancelParticipation(IParticipationDto participation) {
    if (participation.isCancelled()) {
      return false;
    }
    if (participation instanceof IParticipation) {
      ((IParticipation) participation).setCancelled(true);
      return true;
    }

    return false;
  }

  @Override
  public IBusinessDayDto[] getInvitationlessDays() {
    return businessDayDao.fetchInvitationlessDays();
  }

  @Override
  public IBusinessDayDto[] getBusinessDays() {
    return businessDayDao.fetchAll();
  }

  @Override
  public IParticipationDto[] getParticipations(int businessDayId) {
    return participationDao.fetchParticipationsByDate(businessDayId);
  }
}
