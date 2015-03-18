package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IContactDao;
import paoo.cappuccino.util.exception.FatalException;

/**
 * ICompanyDao implementation.
 *
 * @author Kevin Bavay
 */
public class ContactDao implements IContactDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psCreateContact;

  @Inject
  public ContactDao(IEntityFactory entityFactory, IDalBackend dalBackend) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
  }


  @Override
  public IContactDto createContact(IContactDto contact) {
    String query = "INSERT INTO business_days.contacts (contact_id, company, email, email_valid, "
                   + "first_name, last_name, phone,version ) "
                   + "VALUES (DEFAULT, ?, ?,DEFAULT,?,?,?, DEFAULT)"
                   + "RETURNING *";

    try {
      if (psCreateContact == null) {
        psCreateContact = dalBackend.fetchPreparedStatement(query);
      }
      try (ResultSet rs = psCreateContact.executeQuery()) {
        rs.next();
        return makeContactFromSet(rs);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }


  @Override
  public void updateContact(IContactDto contact) {

  }

  @Override
  public IContactDto[] fetchContactByName(String firstName, String lastName) {
    return new IContactDto[0];
  }

  @Override
  public IContactDto[] fetchContactsByCompany(int companyId) {
    return new IContactDto[0];
  }

  private IContactDto makeContactFromSet(ResultSet rs) throws SQLException {
    int contact_id = rs.getInt(1);
    int company = rs.getInt(2);
    String email = rs.getString(3);
    boolean email_valid = rs.getBoolean(4);
    String first_name = rs.getString(5);
    String last_name = rs.getString(6);
    String phone = rs.getString(7);
    int version = rs.getInt(8);
    return entityFactory
        .createContact(contact_id, version, company, email, email_valid, first_name, last_name,
                       phone);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }
    throw new FatalException("Database error", exception);
  }
}
