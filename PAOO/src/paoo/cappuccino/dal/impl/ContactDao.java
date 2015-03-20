package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IContactDao;
import paoo.cappuccino.util.exception.FatalException;

/**
 * IContactDao implementation.
 *
 * @author Kevin Bavay
 */
class ContactDao implements IContactDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private PreparedStatement psCreateContact, psFetchContactByName, psFetchContactsByCompany,
      psUpdateContact;

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
    String query = "UPDATE business_days.contacts SET company = ?, email = ?, "
                   + "first_name = ?, last_name = ?, phone = ?, version = version + 1 "
                   + "WHERE user_id = ? AND version = ?";
    try {
      if (psUpdateContact == null) {
        psUpdateContact = dalBackend.fetchPreparedStatement(query);
      }
      psUpdateContact.setInt(1, contact.getCompany());
      psUpdateContact.setString(2, contact.getEmail());
      //psUpdateContact.setBoolean(3, contact.getEmailValid());
      psUpdateContact.setString(3, contact.getFirstName());
      psUpdateContact.setString(4, contact.getLastName());
      psUpdateContact.setString(5, contact.getPhone());

      int affectedRows = psUpdateContact.executeUpdate();
      if (affectedRows == 0) {
        throw new ConcurrentModificationException(
            "The user with id " + contact.getId() + " and version " + contact.getVersion()
            + " was not found in the database. "
            + "Either it was deleted or modified by another thread.");
      }

    } catch (SQLException e) {
      rethrowSqlException(e);
    }
  }

  @Override
  public IContactDto[] fetchContactByName(String firstName, String lastName) {
    String query = "SELECT * FROM business_days.contacts "
                   + "WHERE first_name LIKE '%?%' AND last_name LIKE '%?%'";
    try {
      if (psFetchContactByName == null) {
        psFetchContactByName = dalBackend.fetchPreparedStatement(query);
      }
      psFetchContactByName.setString(1, firstName);
      psFetchContactByName.setString(2, lastName);

      try (ResultSet rs = psFetchContactByName.executeQuery()) {
        List<IContactDto> contactList = new ArrayList<>();

        while (rs.next()) {
          contactList.add(makeContactFromSet(rs));
        }
        return contactList.toArray(new IContactDto[contactList.size()]);
      }

    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
  }

  @Override
  public IContactDto[] fetchContactsByCompany(int companyId) {
    String query = "SELECT * FROM business_days.contacts WHERE company = ?";
    try {
      if (psFetchContactsByCompany == null) {
        psFetchContactsByCompany = dalBackend.fetchPreparedStatement(query);
      }
      try (ResultSet rs = psFetchContactsByCompany.executeQuery()) {
        List<IContactDto> contactList = new ArrayList<>();

        while (rs.next()) {
          contactList.add(makeContactFromSet(rs));
        }
        return contactList.toArray(new IContactDto[contactList.size()]);
      }

    } catch (SQLException e) {
      rethrowSqlException(e);
    }
    return null;
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
