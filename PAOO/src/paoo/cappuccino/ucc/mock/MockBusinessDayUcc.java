package paoo.cappuccino.ucc.mock;

import java.util.Date;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.ucc.IBusinessDayUcc;

// TODO
public class MockBusinessDayUcc implements IBusinessDayUcc {

  @Override
  public IBusinessDayDto create(Date evenDate) {
    return null;
  }

  @Override
  public ICompanyDto[] addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay) {
    return new ICompanyDto[0];
  }

  @Override
  public boolean changeState(IParticipationDto participation, IParticipationDto.State state) {
    return false;
  }

  @Override
  public IBusinessDayDto[] getInvitationlessDays() {
    return new IBusinessDayDto[0];
  }

  @Override
  public IBusinessDayDto[] getBusinessDays() {
    return new IBusinessDayDto[0];
  }

  @Override
  public ICompanyDto[] getAttendingCompanies(int businessDayId) {
    return new ICompanyDto[0];
  }
}
