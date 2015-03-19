package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
class CompanyDao implements ICompanyDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;

  private PreparedStatement psCreateCompany;
  private PreparedStatement psFetchAll;

  @Inject
  public CompanyDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public ICompanyDto createCompany(ICompanyDto company) {
    ValidationUtil.ensureNotNull(company, "company");

    String query = "INSERT INTO business_days.companies "
                   + "(creator, name, address_street, address_num, "
                   + "address_mailbox, address_postcode, address_town) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?) "
                   + "RETURNING (company_id, creator, name, register_date, address_street, "
                   + "address_num, address_mailbox, address_postcode, address_town,version)";

    try {
      if (psCreateCompany == null) {
        psCreateCompany = dalBackend.fetchPreparedStatement(query);
      }

      psCreateCompany.setInt(1, company.getCreator());

      psCreateCompany.setString(2, company.getName());
      psCreateCompany.setString(3, company.getAddressStreet());
      psCreateCompany.setString(4, company.getAddressNum());
      psCreateCompany.setString(5, company.getAddressMailbox());
      psCreateCompany.setString(6, company.getAddressPostcode());
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
    throw new FatalException("Method not yet supported");
  }

  @Override
  public ICompanyDto[] searchCompanies(String name, String postcode, String street, String town) {
    throw new FatalException("Method not yet supported");
  }

  @Override
  public ICompanyDto[] fetchAll() {
    String query = "SELECT company_id, creator, name, register_date, address_street, "
                   + "address_num, address_mailbox, address_postcode, address_town, version "
                   + "FROM business_days.companies";
    try {
      if (psFetchAll == null) {
        psFetchAll = dalBackend.fetchPreparedStatement(query);
      }

      try (ResultSet rs = psFetchAll.executeQuery()) {
        List<ICompanyDto> companiesList = new ArrayList<>();

        while (rs.next()) {
          companiesList.add(makeCompanyFromSet(rs));
        }

        return companiesList.toArray(new ICompanyDto[companiesList.size()]);
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
    int companyId = rs.getInt(1);
    int creator = rs.getInt(2);
    String name = rs.getString(3);
    LocalDateTime registerDate = rs.getTimestamp(4).toLocalDateTime();

    String addressStreet = rs.getString(5);
    String addressNum = rs.getString(6);
    String addressMailbox = rs.getString(7);
    String addressPostcode = rs.getString(8);
    String addressTown = rs.getString(9);

    int version = rs.getInt(10);

    return entityFactory.createCompany(companyId, version, creator, name, addressStreet, addressNum,
                       addressMailbox, addressPostcode, addressTown, registerDate);
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