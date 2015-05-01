package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
  private PreparedStatement psFetchContactParticipation;
  private PreparedStatement psUpdateAttendance;

  @Inject
  public AttendanceDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IAttendanceDto createAttendance(IAttendanceDto attendance) {
    String query =
        "INSERT INTO business_days.attendances (company, business_day, contact) "
            + "VALUES (?, ?, ?) " + "RETURNING company, business_day, contact,cancelled,version";

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
  public void updateAttendance(IAttendance attendance) {
    String query =
        "UPDATE business_days.attendances " + "SET cancelled = ?," + " version = version + 1 "
            + "WHERE company = ? AND business_day = ? AND contact = ? AND version = ?";

    try {
      if (psUpdateAttendance == null) {
        psUpdateAttendance = dalBackend.fetchPreparedStatement(query);
      }

      psUpdateAttendance.setBoolean(1, attendance.isCancelled());
      psUpdateAttendance.setInt(2, attendance.getCompany());
      psUpdateAttendance.setInt(3, attendance.getBusinessDay());
      psUpdateAttendance.setInt(4, attendance.getContact());
      psUpdateAttendance.setInt(5, attendance.getVersion());


      int affectedRows = psUpdateAttendance.executeUpdate();
      if (affectedRows == 0) {
        throw new ConcurrentModificationException("The attendance with id "
            + attendance.getBusinessDay() + ":" + attendance.getCompany() + ":"
            + attendance.getContact() + " (businessday,company,contact) and version "
            + attendance.getVersion() + " was not found in the database. "
            + "It either was deleted or modified by another thread.");
      }

    } catch (SQLException e) {
      rethrowSqlException(e);
    }
  }


  @Override
  public List<IAttendanceDto> fetchAttendances(int companyId, int businessDayId) {
    String query =
        "SELECT a.company, a.business_day, a.contact, a.cancelled, a.version "
            + "FROM business_days.attendances a " + "WHERE a.company = ? AND a.business_day = ?";

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

  @Override
  public List<IAttendanceDto> fetchAttendancesByContact(int contactId) {
    String query =
        "SELECT a.company, a.business_day, a.contact, a.cancelled, a.version "
            + "FROM business_days.attendances a " 
            + "WHERE a.contact =  ?";

    try {
      if (psFetchContactParticipation == null) {
        psFetchContactParticipation = dalBackend.fetchPreparedStatement(query);
      }

      psFetchContactParticipation.setInt(1, contactId);

      try (ResultSet rs = psFetchContactParticipation.executeQuery()) {
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
    boolean cancelled = rs.getBoolean(4);
    int version = rs.getInt(5);

    return entityFactory.createAttendance(company, businessDay, contact, cancelled, version);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("pkey")) {
      throw new NonUniqueFieldException(
          "There is already a participation for that contact at that business day.");
    }

    throw new FatalException("Database error", exception);
  }



}
