package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import paoo.cappuccino.business.dto.IParticipationDto;
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
public class ParticipationDao implements IParticipationDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psFetchParticipationsByCompany;
  private PreparedStatement psFetchParticipationsByDate;
  private PreparedStatement psCreateParticipation;

  @Inject
  public ParticipationDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public IParticipationDto createParticipation(IParticipationDto participation) {
    String query = "INSERT INTO business_days.participations (company, business_day) "
                   + "VALUES (?, ?) "
                   + "RETURNING (company, business_day, state, cancelled, version);";
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
    //TODO
  }

  @Override
  public IParticipationDto[] fetchParticipationsByDate(int businessDayId) {
    String query = "SELECT * FROM business_days.participations WHERE business_day = ?";

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
        return participationList.toArray(new IParticipationDto[participationList.size()]);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return new IParticipationDto[0];
  }

  @Override
  public IParticipationDto[] fetchParticipationsByCompany(int companyId) {
    String query = "SELECT * FROM business_days.participations WHERE company = ?";

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
        return participationList.toArray(new IParticipationDto[participationList.size()]);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return new IParticipationDto[0];
  }

  private IParticipationDto makeParticipationFromSet(ResultSet rs) throws SQLException {
    int company = rs.getInt(1);
    int businessDay = rs.getInt(2);
    IParticipationDto.State state = IParticipationDto.State.valueOf(rs.getString(3));
    boolean concelled = rs.getBoolean(4);
    int version = rs.getInt(5);

    return entityFactory.createParticipation(company, businessDay, concelled, version, state);
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
