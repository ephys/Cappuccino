package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
class ContactDao implements IContactDao {

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
    String query = "INSERT INTO business_days.contacts (company, email, email_valid, "
                   + "first_name, last_name, phone) "
                   + "VALUES (?, ?, ?, ?, ?, ?)"
                   + "RETURNING contact_id, company, email, email_valid, first_name, last_name, "
                   + "phone,version";

    try {
      if (psCreateContact == null) {
        psCreateContact = dalBackend.fetchPreparedStatement(query);
      }

      psCreateContact.setInt(1, contact.getCompany());
      psCreateContact.setString(2, contact.getEmail());
      if (contact.getEmail() == null) {
        psCreateContact.setNull(3, Types.BOOLEAN);
      } else {
        psCreateContact.setBoolean(3, true);
      }
      psCreateContact.setString(4, contact.getFirstName());
      psCreateContact.setString(5, contact.getLastName());
      psCreateContact.setString(6, contact.getPhone());

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
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public IContactDto[] fetchContactByName(String firstName, String lastName) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Override
  public IContactDto[] fetchContactsByCompany(int companyId) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  private IContactDto makeContactFromSet(ResultSet rs) throws SQLException {
    int contactId = rs.getInt(1);
    int companyId = rs.getInt(2);
    String email = rs.getString(3);
    boolean emailValid = rs.getBoolean(4);
    String firstName = rs.getString(5);
    String lastName = rs.getString(6);
    String phone = rs.getString(7);
    int version = rs.getInt(8);

    return entityFactory.createContact(contactId, version, companyId, email, emailValid, firstName,
                                       lastName, phone);
  }

  private void rethrowSqlException(SQLException exception) {
    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }

    throw new FatalException("Database error", exception);
  }
}
