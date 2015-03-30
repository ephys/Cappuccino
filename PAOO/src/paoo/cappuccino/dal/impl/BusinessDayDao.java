package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.DateUtils;
import paoo.cappuccino.util.ValidationUtil;
import paoo.cappuccino.util.exception.FatalException;

/**
 * IBusinessDayDao implementation.
 *
 * @author Kevin Bavay
 */
class BusinessDayDao implements IBusinessDayDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;

  private PreparedStatement psCreateBusinessDay;
  private PreparedStatement psFetchAll;
  private PreparedStatement psFetchByDate;
  private PreparedStatement psFetchInvitationlessDays;
  private PreparedStatement psFetchBusinessDayById;

  @Inject
  public BusinessDayDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IBusinessDayDto createBusinessDay(IBusinessDayDto businessDay) {
    ValidationUtil.ensureNotNull(businessDay, "businessDay");

    String query =
        "INSERT INTO business_days.business_days(business_day_id, event_date, creation_date, "
        + "academic_year, version) "
        + "VALUES (DEFAULT, ?, DEFAULT, ?, DEFAULT)"
        + "RETURNING (business_day_id, event_date, creation_date, version)";

    try {
      if (psCreateBusinessDay == null) {
        psCreateBusinessDay = dalBackend.fetchPreparedStatement(query);
      }

      psCreateBusinessDay.setTimestamp(1, Timestamp.valueOf(businessDay.getEventDate()));
      psCreateBusinessDay.setString(2, DateUtils.getAcademicYearStr(businessDay.getEventDate()));

      try (ResultSet rs = psCreateBusinessDay.executeQuery()) {
        rs.next();
        return makeBusinessDaysFromSet(rs);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }

  @Override
  public IBusinessDayDto[] fetchAll() {
    String query = "SELECT d.business_day_id, d.event_date, d.creation_date, "
                   + "d.academic_year, d.version "
                   + "FROM business_days.business_days d";

    try {
      if (psFetchAll == null) {
        psFetchAll = dalBackend.fetchPreparedStatement(query);
      }

      try (ResultSet rs = psFetchAll.executeQuery()) {
        List<IBusinessDayDto> businessDayList = new ArrayList<>();

        while (rs.next()) {
          businessDayList.add(makeBusinessDaysFromSet(rs));
        }

        return businessDayList.toArray(new IBusinessDayDto[businessDayList.size()]);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return new IBusinessDayDto[0];
  }

  @Override
  public IBusinessDayDto[] fetchInvitationlessDays() {
    String query = "SELECT DISTINCT d.business_day_id, d.event_date, d.creation_date, "
                   + "d.academic_year, d.version "
                   + "FROM business_days.business_days d"
                   + "WHERE (SELECT COUNT(p.*) FROM business_days.participations p"
                   + "WHERE p.business_day = d.business_day_id)";

    try {
      if (psFetchInvitationlessDays == null) {
        psFetchInvitationlessDays = dalBackend.fetchPreparedStatement(query);
      }

      try (ResultSet rs = psFetchInvitationlessDays.executeQuery()) {
        List<IBusinessDayDto> businessDayList = new ArrayList<>();

        while (rs.next()) {
          businessDayList.add(makeBusinessDaysFromSet(rs));
        }

        return businessDayList.toArray(new IBusinessDayDto[businessDayList.size()]);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return new IBusinessDayDto[0];
  }

  @Override
  public IBusinessDayDto fetchBusinessDaysByDate(int year) {
    String query =
        "SELECT d.business_day_id, d.event_date, d.creation_date, d.academic_year, d.version"
        + " FROM business_days.business_days d WHERE academic_year = ?";

    try {
      if (psFetchByDate == null) {
        psFetchByDate = dalBackend.fetchPreparedStatement(query);
      }

      psFetchByDate.setString(1, (year + "-" + (year + 1)));

      try (ResultSet rs = psFetchByDate.executeQuery()) {
        if (rs.next()) {
          return makeBusinessDaysFromSet(rs);
        }
        return null;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }

  @Override
  public IBusinessDayDto fetchBusinessDayById(int id) {
    String query = "SELECT * FROM business_days.business_days WHERE business_day_id = ?";
    try {
      if (psFetchBusinessDayById == null) {
        psFetchBusinessDayById = dalBackend.fetchPreparedStatement(query);
      }

      psFetchBusinessDayById.setInt(1, id);

      try (ResultSet rs = psFetchBusinessDayById.executeQuery()) {
        if (rs.next()) {
          return makeBusinessDaysFromSet(rs);
        }
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }

  private IBusinessDayDto makeBusinessDaysFromSet(ResultSet set) throws SQLException {
    int id = set.getInt(1);
    LocalDateTime eventDate = set.getTimestamp(2).toLocalDateTime();
    LocalDateTime creationDate = set.getTimestamp(3).toLocalDateTime();
    int version = set.getInt(4);

    return entityFactory.createBusinessDay(id, version, eventDate, creationDate);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("academic_year_key")) {
      throw new NonUniqueFieldException("There is already a business day for that year.");
    }
    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }
    throw new FatalException("Database error", exception);
  }
}
