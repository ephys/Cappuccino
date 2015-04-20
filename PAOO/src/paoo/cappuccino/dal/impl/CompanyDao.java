package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.ICompanyDao;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.DateUtils;
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
  private PreparedStatement psUpdateCompany;
  private PreparedStatement psSearchCompanies;
  private PreparedStatement psFetchCompaniesByDay;
  private PreparedStatement psFetchInvitableCompanies;
  private PreparedStatement psFetchCompanyById;

  @Inject
  public CompanyDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }

  @Override
  public ICompanyDto createCompany(ICompanyDto company) {
    ValidationUtil.ensureNotNull(company, "company");

    String query =
        "INSERT INTO business_days.companies(creator, name, address_street, address_num, "
            + " address_mailbox, address_postcode, address_town) "
            + " VALUES (?, ?, ?, ?, ?, ?, ?) "
            + "RETURNING company_id, creator, name, register_date, address_street, "
            + " address_num, address_mailbox, address_postcode, address_town,version ;";

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
    ValidationUtil.ensureNotNull(company, "company");

    String query =
        "UPDATE business_days.companies "
            + "SET creator = ?, name = ?, register_date = ?, adress_street = ?, "
            + "adress_num = ?, adresse_mailbox = ?, adresse_postcode = ?, "
            + "adresse_town = ?, version = version + 1 "
            + "WHERE company_id = ? AND version = ? LIMIT 1";

    try {
      if (psUpdateCompany == null) {
        psUpdateCompany = dalBackend.fetchPreparedStatement(query);
      }

      psUpdateCompany.setInt(1, company.getCreator());
      psUpdateCompany.setString(2, company.getName());
      psUpdateCompany.setString(3, company.getRegisterDate().toString());
      psUpdateCompany.setString(4, company.getAddressStreet());
      psUpdateCompany.setString(5, company.getAddressNum());
      psUpdateCompany.setString(6, company.getAddressMailbox());
      psUpdateCompany.setString(7, company.getAddressPostcode());
      psUpdateCompany.setString(8, company.getAddressTown());

      psUpdateCompany.setInt(9, company.getId());
      psUpdateCompany.setInt(10, company.getVersion());

      int affectedRows = psUpdateCompany.executeUpdate();
      if (affectedRows == 0) {
        throw new ConcurrentModificationException("The company with id " + company.getId()
            + " and version " + company.getVersion() + " was not found in the database. "
            + "Either it was deleted or " + "modified by another thread.");
      } else if (company instanceof ICompany) {
        ((ICompany) company).incrementVersion();
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
  }

  @Override
  public List<ICompanyDto> searchCompanies(String name, String postcode, 
                                           String street, String town) {
    String query =
        "SELECT * FROM business_days.companies WHERE " + " (? IS NULL OR LOWER(name) LIKE ?) AND "
            + " (? IS NULL OR LOWER(address_street) LIKE ?) AND "
            + " (? IS NULL OR LOWER(address_town) LIKE ?) AND "
            + " (? IS NULL OR LOWER(address_postcode) = ?)";

    try {
      if (psSearchCompanies == null) {
        psSearchCompanies = dalBackend.fetchPreparedStatement(query);
      }

      if (name != null) {
        name = '%' + name.toLowerCase() + '%';
      }

      if (street != null) {
        street = '%' + street.toLowerCase() + '%';
      }

      if (town != null) {
        town = '%' + town.toLowerCase() + '%';
      }

      if (postcode != null) {
        postcode = postcode.toLowerCase();
      }

      // setting them twice for nullable check :/
      psSearchCompanies.setString(1, name);
      psSearchCompanies.setString(2, name);

      psSearchCompanies.setString(3, street);
      psSearchCompanies.setString(4, street);

      psSearchCompanies.setString(5, town);
      psSearchCompanies.setString(6, town);

      psSearchCompanies.setString(7, postcode);
      psSearchCompanies.setString(8, postcode);

      try (ResultSet rs = psSearchCompanies.executeQuery()) {
        List<ICompanyDto> companiesList = new ArrayList<>();

        while (rs.next()) {
          companiesList.add(makeCompanyFromSet(rs));
        }

        return companiesList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    throw new FatalException("Unreachable statement");
  }

  @Override
  public List<ICompanyDto> fetchAll() {
    String query =
        "SELECT c.company_id, c.creator, c.name, c.register_date, c.address_street, "
            + "c.address_num, c.address_mailbox, c.address_postcode, c.address_town, c.version "
            + "FROM business_days.companies c";

    try {
      if (psFetchAll == null) {
        psFetchAll = dalBackend.fetchPreparedStatement(query);
      }

      try (ResultSet rs = psFetchAll.executeQuery()) {
        List<ICompanyDto> companiesList = new ArrayList<>();

        while (rs.next()) {
          companiesList.add(makeCompanyFromSet(rs));
        }

        return companiesList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    throw new FatalException("Unreachable statement");
  }

  @Override
  public List<ICompanyDto> fetchInvitableCompanies() {
    /**
     * Le SELECT utilise le système d'année academique. Premier SELECT : soit entreprise enregistrée
     * comme nouvelle entreprise dans l’année écoulée. Second SELECT : soit entreprise ayant
     * participé, au moins 1 x, dans les 4 années précédentes et ayant payé sa participation.
     */
    String query =
        "SELECT c.company_id, c.creator, c.name, c.register_date, \n"
            + "c.address_street, c.address_num, c.address_mailbox, \n"
            + "c.address_postcode, c.address_town, c.version "
            + "FROM business_days.companies c \n"
            + "WHERE c.company_id IN ( \n"
            + "SELECT p.company FROM business_days.participations p, "
            + "       business_days.business_days b \n"
            + "      WHERE p.business_day = b.business_day_id \n"
                  + "AND b.academic_year >= (? - 4) "
            + "      AND p.state = 'PAID'\n"
            + ") OR now() - c.register_date <= INTERVAL '1' YEAR  \n";
/*
    String query = "SELECT company_id, creator, name, register_date, address_street, address_num, "
                   + "      address_mailbox, address_postcode, address_town, version "
                   + "FROM business_days.companies "
                   + "WHERE company_id NOT IN (SELECT company FROM business_days.participations) "
                   + "  AND ( "
                   + "    date_part('month', now()) BETWEEN 1 AND 6 "
                   + "    AND date_part('years', register_date) = date_part('years', now()) "
                   + "    OR date_part('years', register_date) = "
                   + "                    date_part('years', now() - INTERVAL '1 year') "
                   + "    AND date_part('month', register_date) BETWEEN 6 AND 12 "
                   + "  ) "
                   + "  OR ( "
                   + "    date_part('month', now()) BETWEEN 6 AND 12 "
                   + "    AND date_part('years', register_date) = date_part('years', now()) "
                   + "    AND date_part('month', register_date) BETWEEN 6 AND 12 "
                   + ") "
                   + "UNION "
                   + "SELECT company_id, creator, name, register_date,address_street, address_num, "
                   + "       address_mailbox, address_postcode, address_town, companies.version "
                   + "FROM business_days.companies, business_days.participations, "
                   + "      business_days.business_days "
                   + "WHERE company = companies.company_id "
                   + "AND business_day = business_days.business_day_id "
                   + "AND participations.state = 'PAID' "
                   + "AND ( "
                   + "  date_part('month', now()) BETWEEN 1 AND 6 "
                   + "  AND academic_year >= date_part('years', now() - INTERVAL '5 year') "
                   + ") "
                   + "OR ( "
                   + "  date_part('month', now()) BETWEEN 6 AND 12 "
                   + "  AND academic_year >= date_part('years', now() - INTERVAL '4 year') "
                   + ");";
*/

    try {
      if (psFetchInvitableCompanies == null) {
        psFetchInvitableCompanies = dalBackend.fetchPreparedStatement(query);
      }
      
      psFetchInvitableCompanies
        .setInt(1, DateUtils.getAcademicYear(LocalDateTime.now()));

      try (ResultSet rs = psFetchInvitableCompanies.executeQuery()) {
        List<ICompanyDto> companiesList = new ArrayList<>();
        while (rs.next()) {
          companiesList.add(makeCompanyFromSet(rs));
        }
        return companiesList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    throw new FatalException("Unreachable statement");
  }

  @Override
  public List<ICompanyDto> fetchCompaniesByDay(int businessDayId) {
    String query =
        "SELECT c.company_id, c.creator, c.name, c.register_date, c.address_street, "
            + " c.address_num, c.address_mailbox, c.address_postcode, c.address_town, "
            + " c.version " + "FROM business_days.participations p, business_days.companies c"
            + " WHERE p.business_day = ?" + " AND p.company = c.company_id";

    try {
      if (psFetchCompaniesByDay == null) {
        psFetchCompaniesByDay = dalBackend.fetchPreparedStatement(query);
      }
      psFetchCompaniesByDay.setInt(1, businessDayId);

      try (ResultSet rs = psFetchCompaniesByDay.executeQuery()) {
        List<ICompanyDto> companiesList = new ArrayList<>();
        while (rs.next()) {
          companiesList.add(makeCompanyFromSet(rs));
        }

        return companiesList;
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    throw new FatalException("Unreachable statement");
  }

  @Override
  public ICompanyDto fetchCompanyById(int id) {
    String query =
        "SELECT c.company_id, c.creator, c.name, c.register_date, c.address_street, "
            + " c.address_num, c.address_mailbox, c.address_postcode, c.address_town, "
            + " c.version " + "FROM business_days.companies c " + "WHERE c.company_id = ? LIMIT 1";

    try {
      if (psFetchCompanyById == null) {
        psFetchCompanyById = dalBackend.fetchPreparedStatement(query);
      }

      psFetchCompanyById.setInt(1, id);

      try (ResultSet rs = psFetchCompanyById.executeQuery()) {
        if (rs.next()) {
          return makeCompanyFromSet(rs);
        }
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
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

    return entityFactory.createCompany(companyId, version, creator, name, addressStreet,
        addressNum, addressMailbox, addressPostcode, addressTown, registerDate);
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
