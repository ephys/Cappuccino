package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.dal.exception.ConnectionException;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.ValidationUtil;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * IUserDao implementation.
 *
 * @author Kevin Bavay
 */
class UserDao implements IUserDao {

  private final IEntityFactory entityFactory;
  private final IDalBackend dalBackend;
  private final IStringHasher hasher;

  @Inject
  public UserDao(IEntityFactory entityFactory, IDalBackend dalBackend, IStringHasher hasher) {
    this.entityFactory = entityFactory;
    this.dalBackend = dalBackend;
    this.hasher = hasher;
  }

  private IUser makeUserFromSet(ResultSet set) throws SQLException {
    int id = set.getInt(1);
    IUserDto.Role role = IUserDto.Role.valueOf(set.getString(2));
    IHashHolderDto password = hasher.unserialize(set.getString(3));
    String email = set.getString(4);
    String username = set.getString(5);
    String firstName = set.getString(6);
    String lastName = set.getString(7);
    LocalDateTime registerDate = Timestamp.valueOf(set.getString(8)).toLocalDateTime();
    int version = set.getInt(9);

    return entityFactory.createUser(id, version, username, password, lastName,
                                    firstName, email, role, registerDate);
  }

  private void rethrowSqlException(SQLException exception)
      throws IllegalArgumentException, ConnectionException, NonUniqueFieldException {
    if (exception.getMessage().contains("users_username_key")) {
      throw new NonUniqueFieldException("The user's username already exists.");
    }

    if (exception.getMessage().contains("violates")) {
      throw new IllegalArgumentException(
          "One of the fields failed to insert due to constraint violations.");
    }

    throw new ConnectionException("Database error", exception);
  }

  @Override
  public IUser createUser(IUserDto user) {
    ValidationUtil.ensureNotNull(user, "user");

    String query =
        "INSERT INTO business_days.users(user_id, role, password, email, username,"
        + " first_name, last_name, register_date, version) "
        + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT) "
        + "RETURNING (user_id, role, password, email, username,"
        + " first_name, last_name, register_date, version)";

    try (PreparedStatement ps = dalBackend.fetchPrepardedStatement(query)) {
      ps.setString(1, user.getRole().toString());
      ps.setString(2, hasher.serialize(user.getPassword()));
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getUsername());
      ps.setString(5, user.getFirstName());
      ps.setString(6, user.getLastName());

      try (ResultSet set = ps.executeQuery()) {
        set.next();

        return makeUserFromSet(set);
      }
    } catch (SQLException e) {
      rethrowSqlException(e);
    }

    return null;
  }

  @Override
  public IUser fetchUserByUsername(String username) {
    ValidationUtil.ensureFilled(username, "username");

    String query = "SELECT user_id, role, password, email, username, first_name, last_name, "
                   + "register_date, version FROM business_days.users WHERE username = ?";

    try (PreparedStatement ps = dalBackend.fetchPrepardedStatement(query)) {
      ps.setString(1, username);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return makeUserFromSet(rs);
        }

        return null;
      }
    } catch (SQLException e) {
      throw new ConnectionException("Database error", e);
    }
  }

  @Override
  public void updateUser(IUser user) {
    ValidationUtil.ensureNotNull(user, "user");

    String query =
        "UPDATE business_days.users SET password = ?, email = ?, first_name = ?, last_name = ?,"
        + " version = version + 1"
        + "WHERE user_id = ? AND version = ?";

    try (PreparedStatement ps = dalBackend.fetchPrepardedStatement(query)) {
      ps.setString(1, hasher.serialize(user.getPassword()));
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getFirstName());
      ps.setString(4, user.getLastName());
      ps.setInt(5, user.getId());
      ps.setInt(6, user.getVersion());

      int affectedRows = ps.executeUpdate();
      if (affectedRows == 0) {
        throw new ConcurrentModificationException(
            "The user with id " + user.getId() + " and version " + user.getVersion()
            + " was not found in the database. "
            + "Either it was deleted or modified by another thread.");
      }

      user.incrementVersion();
    } catch (SQLException e) {
      rethrowSqlException(e);
    }
  }

  @Override
  public IUser getCompanyCreator(int company) {
    return null;
  }
}
