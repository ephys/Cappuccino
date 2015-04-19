package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import paoo.cappuccino.business.dto.IAttendanceDto;
import paoo.cappuccino.business.entity.IAttendance;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IAttendanceDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.exception.FatalException;

public class AttendanceDao implements IAttendanceDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psCreateParticipation;
  private PreparedStatement psFetchParticipation;

  @Inject
  public AttendanceDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IAttendanceDto createAttendance(IAttendanceDto attendance) {
    String query = "INSERT INTO business_days.attendances (company, business_day, contact) "
                   + "VALUES (?, ?, ?) "
                   + "RETURNING company, business_day, contact";

    try {
      if (psCreateParticipation == null) {
        psCreateParticipation = dalBackend.fetchPreparedStatement(query);
      }

      psCreateParticipation.setInt(1, attendance.getCompany());
      psCreateParticipation.setInt(2, attendance.getBusinessDay());
      psCreateParticipation.setInt(3, attendance.getContact());

      try (ResultSet rs = psCreateParticipation.executeQuery()) {
        if (rs.next()) {
          return makeEntityFromSet(rs);
        }
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    return null;
  }

  @Override
  public List<IAttendanceDto> fetchAttendances(int companyId, int businessDayId) {
    String query = "SELECT a.company, a.business_day, a.contact FROM business_days.attendances a "
                   + "WHERE a.company = ? AND a.business_day = ?";

    try {
      if (psFetchParticipation == null) {
        psFetchParticipation = dalBackend.fetchPreparedStatement(query);
      }

      psFetchParticipation.setInt(1, companyId);
      psFetchParticipation.setInt(2, businessDayId);

      try (ResultSet rs = psFetchParticipation.executeQuery()) {
        List<IAttendanceDto> attendances = new ArrayList<>();
        while (rs.next()) {
          attendances.add(makeEntityFromSet(rs));
        }

        return attendances;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    throw new FatalException("unreachable statement");
  }

  private IAttendance makeEntityFromSet(ResultSet rs) throws SQLException {
    int company = rs.getInt(1);
    int businessDay = rs.getInt(2);
    int contact = rs.getInt(3);

    return entityFactory.createAttendance(company, businessDay, contact);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("pkey")) {
      throw new NonUniqueFieldException(
          "There is already a participation for that contact at that business day.");
    }

    throw new FatalException("Database error", exception);
  }
}
