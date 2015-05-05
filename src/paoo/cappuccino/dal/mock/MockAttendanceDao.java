package paoo.cappuccino.dal.mock;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.IAttendance;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IAttendanceDao;

class MockAttendanceDao implements IAttendanceDao {

  private final List<IAttendance> attendances = new ArrayList<>();
  private final IEntityFactory factory;

  @Inject
  public MockAttendanceDao(IEntityFactory entityFactory) {
    this.factory = entityFactory;
  }

  @Override
  public IAttendanceDto createAttendance(IAttendanceDto attendance) {
    IAttendance attendanceEntity = factory.createAttendance(attendance.getCompany(),
                                                            attendance.getBusinessDay(),
                                                            attendance.getContact());

    attendances.add(attendanceEntity);

    return attendanceEntity;
  }

  @Override
  public List<IAttendanceDto> fetchAttendances(int companyId, int businessDayId) {
    return attendances
        .stream()
        .filter(attendance -> attendance.getCompany() == companyId
                              && attendance.getBusinessDay() == businessDayId)
        .collect(Collectors.toList());
  }

  @Override
  public List<IAttendanceDto> fetchAttendancesByContact(int contactId) {
    return attendances.stream()
        .filter(attendance -> attendance.getContact() == contactId)
        .collect(Collectors.toList());
  }

  @Override
  public void updateAttendance(IAttendanceDto attendance) {
    for (IAttendance local : attendances) {
      if (attendance.getCompany() == local.getCompany()
          && attendance.getContact() == local.getCompany()
          && attendance.getBusinessDay() == local.getBusinessDay()
          && attendance.getVersion() == local.getVersion()) {
        local.setCancelled(attendance.isCancelled());
        local.incrementVersion();

        if (attendance instanceof IAttendance) {
          ((IAttendance) attendance).incrementVersion();
        }

        return;
      }
    }

    throw new ConcurrentModificationException("could not find matching attendance");
  }
}
