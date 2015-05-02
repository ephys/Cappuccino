package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IParticipationDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.exception.FatalException;

/**
 * IParticipationDao implementation.
 *
 * @author Kevin Bavay
 */
class ParticipationDao implements IParticipationDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psFetchParticipationsByCompany;
  private PreparedStatement psFetchParticipationsByDate;
  private PreparedStatement psCreateParticipation;
  private PreparedStatement psUpdateParticipation;

  @Inject
  public ParticipationDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IParticipationDto createParticipation(IParticipationDto participation) {
    String query =
        "INSERT INTO business_days.participations (company, business_day) " + "VALUES (?, ?) "
            + "RETURNING company, business_day, state, cancelled, version";
    try {
      if (psCreateParticipation == null) {
        psCreateParticipation = dalBackend.fetchPreparedStatement(query);
      }

      psCreateParticipation.setInt(1, participation.getCompany());
      psCreateParticipation.setInt(2, participation.getBusinessDay());

      try (ResultSet rs = psCreateParticipation.executeQuery()) {
        if (rs.next()) {
          return makeParticipationFromSet(rs);
        }
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    return null;
  }

  @Override
  public void updateParticipation(IParticipationDto participation) {
    String query =
        "UPDATE business_days.participations "
            + "SET state = ?::business_days.participation_state, cancelled = ?,"
            + " version = version + 1 "
            + "WHERE company = ? AND business_day = ? AND version = ?";

    try {
      if (psUpdateParticipation == null) {
        psUpdateParticipation = dalBackend.fetchPreparedStatement(query);
      }

      psUpdateParticipation.setString(1, participation.getState().name());
      psUpdateParticipation.setBoolean(2, participation.isCancelled());

      psUpdateParticipation.setInt(3, participation.getCompany());
      psUpdateParticipation.setInt(4, participation.getBusinessDay());
      psUpdateParticipation.setInt(5, participation.getVersion());

      int affectedRows = psUpdateParticipation.executeUpdate();
      if (affectedRows == 0) {
        throw new ConcurrentModificationException("The user with id " + participation.getCompany()
            + ":" + participation.getBusinessDay() + " (company, businessday) and version "
            + participation.getVersion() + " was not found in the database. "
            + "It either was deleted or modified by another thread.");
      }

      if (participation instanceof IParticipation) {
        ((IParticipation) participation).incrementVersion();
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
  }

  @Override
  public List<IParticipationDto> fetchParticipationsByDate(int businessDayId) {
    String query = "SELECT p.company, p.business_day, p.state, p.cancelled, p.version "
                   + "FROM business_days.participations p "
                   + "WHERE p.business_day = ?";

    try {
      if (psFetchParticipationsByDate == null) {
        psFetchParticipationsByDate = dalBackend.fetchPreparedStatement(query);
      }

      psFetchParticipationsByDate.setInt(1, businessDayId);

      try (ResultSet rs = psFetchParticipationsByDate.executeQuery()) {
        List<IParticipationDto> participationList = new ArrayList<>();

        while (rs.next()) {
          participationList.add(makeParticipationFromSet(rs));
        }

        return participationList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    return null;
  }

  @Override
  public List<IParticipationDto> fetchParticipationsByCompany(int companyId) {
    String query = "SELECT p.company, p.business_day, p.state, p.cancelled, p.version "
                   + "FROM business_days.participations p "
                   + "WHERE p.company = ?"
                   + " AND p.cancelled = FALSE"
                   + " AND p.state <> DECLINED";

    try {
      if (psFetchParticipationsByCompany == null) {
        psFetchParticipationsByCompany = dalBackend.fetchPreparedStatement(query);
      }

      psFetchParticipationsByCompany.setInt(1, companyId);

      try (ResultSet rs = psFetchParticipationsByCompany.executeQuery()) {
        List<IParticipationDto> participationList = new ArrayList<>();

        while (rs.next()) {
          participationList.add(makeParticipationFromSet(rs));
        }

        return participationList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    return null;
  }

  private IParticipationDto makeParticipationFromSet(ResultSet rs) throws SQLException {
    int company = rs.getInt(1);
    int businessDay = rs.getInt(2);
    IParticipationDto.State state = IParticipationDto.State.valueOf(rs.getString(3));
    boolean cancelled = rs.getBoolean(4);
    int version = rs.getInt(5);

    return entityFactory.createParticipation(company, businessDay, cancelled, version, state);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("participations_pkey")) {
      throw new NonUniqueFieldException(
          "There is already a participation of this company at that business day.");
    }
    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }

    throw new FatalException("Database error", exception);
  }
}
