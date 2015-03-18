package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.ValidationUtil;
import paoo.cappuccino.util.exception.FatalException;

/**
 * ICompanyDao implementation.
 *
 * @author Kevin Bavay
 */
public class CompanyDao implements ICompanyDao {


  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psCreateCompany, psFetchAll;

  @Inject
  public CompanyDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public ICompanyDto createCompany(ICompanyDto company) {
    ValidationUtil.ensureNotNull(company, "company");

    String query = "INSERT INTO business_days.companies "
                   + "(company_id, creator, name, register_date, adress_street, adress_num, "
                   + "adresse_mailbox, adresse_postcode, adresse_town,version ) "
                   + "VALUES (DEFAULT, ?, ?,DEFAULT,?,?, "
                   + "?,?, ?, DEFAULT) RETURNING *";
    try {

      if (psCreateCompany == null) {
        psCreateCompany = dalBackend.fetchPreparedStatement(query);
      }
      psCreateCompany.setInt(1, company.getCreator());
      psCreateCompany.setString(2, company.getName());
      psCreateCompany.setString(3, company.getAddressStreet());
      psCreateCompany.setInt(4, Integer.parseInt(company.getAddressNum()));
      psCreateCompany.setString(5, company.getAddressMailbox());
      psCreateCompany.setInt(6, Integer.parseInt(company.getAddressPostcode()));
      psCreateCompany.setString(7, company.getAddressTown());

      try (ResultSet rs = psCreateCompany.executeQuery()) {
        rs.next();
        return makeCompanyFromSet(rs);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }


  @Override
  public void updateCompany(ICompanyDto company) {
    throw new FatalException("Method not supported yet");
  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postcode, String street, String town) {
    throw new FatalException("Method not supported yet");
  }

  @Override
  public ICompanyDto[] fetchAll() {
    String query = "SELECT * FROM business_days.companies";
    try {
      if (psFetchAll == null) {
        dalBackend.fetchPreparedStatement(query);
      }
      try (ResultSet rs = psFetchAll.executeQuery()) {
        ICompanyDto[] companiesTable = new ICompanyDto[rs.getFetchSize()];
        for (int i = 0; rs.next(); i++) {
          companiesTable[i] = makeCompanyFromSet(rs);
        }
        return companiesTable;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }

  @Override
  public ICompanyDto[] fetchInvitableCompanies() {
    throw new FatalException("Method not supported yet");
  }

  @Override
  public ICompanyDto[] fetchCompaniesByDay(int businessDayId) {
    throw new FatalException("Method not supported yet");
  }

  private ICompanyDto makeCompanyFromSet(ResultSet rs) throws SQLException {
    int company_id = rs.getInt(1);
    int creator = rs.getInt(2);
    String name = rs.getString(3);
    LocalDateTime register_date = Timestamp.valueOf(rs.getString(4)).toLocalDateTime();
    String adress_street = rs.getString(5);
    String adress_num = "" + rs.getInt(6);
    String adress_mailbox = rs.getString(7);
    String adress_postcode = "" + rs.getInt(8);
    String adress_town = rs.getString(9);
    int version = rs.getInt(10);
    return entityFactory
        .createCompany(company_id, version, creator, name, adress_street, adress_num,
                       adress_mailbox, adress_postcode, adress_town, register_date);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("name_key")) {
      throw new NonUniqueFieldException("There is already a company with that name.");
    }

    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }

    throw new FatalException("Database error", exception);
  }
}
