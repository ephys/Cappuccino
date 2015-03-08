package paoo.cappuccino.dal.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.dal.exception.ConnectionException;
import paoo.cappuccino.dal.exception.NonUniqueFieldException;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * Implements de IUserDao
 *
 * @author Kevin Bavay
 */
class UserDao implements IUserDao {

  @Inject
  private IEntityFactory entityFactory;
  @Inject
  private IDalBackend iDalBackend;
  @Inject
  private IStringHasher hasher;

  @Override
  public IUser createUser(IUserDto user) {
    String query =
        "INSERT INTO businessDays.users VALUES ( DEFAULT," + user.getRole().toString() + ","
            + hasher.serialize(user.getPassword()) + "," + user.getEmail() + ","
            + user.getUsername() + "," + user.getFirstName() + "," + user.getLastName()
            + ",DEFAULT, DEFAULT);";
    PreparedStatement ps;
    try {
      ps = iDalBackend.fetchPrepardedStatement(query);
      ps.execute();
      ps.close();
    } catch (SQLException e) {
      if (e.getMessage().contains("users_username_key")) {
        throw new NonUniqueFieldException("Le username existe déjà");
      }
      if (e.getMessage().contains("violates")) {
        throw new IllegalArgumentException(
            "One of the fields failed to insert due to constraint violations.");
      }
    }
    return fetchUserByUsername(user.getUsername());
  }

  @Override
  public IUser fetchUserByUsername(String username) {
    String query = "SELECT * FROM businessDays.users WHERE username LIKE '" + username + "'";
    PreparedStatement ps;
    ResultSet rs;
    try {
      ps = iDalBackend.fetchPrepardedStatement(query);
      rs = ps.executeQuery();

      if (rs.next()) {
        java.sql.Timestamp register_date =
            java.sql.Timestamp.valueOf(rs.getString("register_date"));
        return entityFactory.createUser(rs.getInt("users_id"), rs.getInt("version"),
            rs.getString("username"), hasher.unserialize(rs.getString("password")),
            rs.getString("last_name"), rs.getString("first_name"), rs.getString("email"),
            IUserDto.Role.valueOf(rs.getString("role")), register_date.toLocalDateTime());
        // TODO fermer les ps et rs
      } else {
        throw new ConnectionException("No user return");
      }
    } catch (ConnectionException e) {
      throw new ConnectionException("Could not update the user", e);
    } catch (SQLException e) {
      throw new ConnectionException("Database error", e);
    }
  }

  @Override
  public void updateUser(IUser user) {
    // param check
    if (user == null) {
      throw new IllegalArgumentException("The parametre can't be null");
    }
    try {
      PreparedStatement ps;
      ResultSet rs;

      // version check
      ps =
          iDalBackend
              .fetchPrepardedStatement("SELECT version FROM businessdays.users WHERE users_id LIKE '"
                  + user.getId() + "'");
      rs = ps.executeQuery();
      if (rs.next() && rs.getInt("version") != user.getVersion()) {
        throw new ConcurrentModificationException("La version ne correspond pas");
      }

      String query =
          "UPDATE businessDays.users SET username, password, email, first_name, last_name = ("
              + user.getUsername() + "," + hasher.serialize(user.getPassword()) + ","
              + user.getEmail() + "," + user.getFirstName() + "," + user.getLastName()
              + " )  WHERE users_id LIKE '" + user.getId() + "'";

      ps = iDalBackend.fetchPrepardedStatement(query);
      ps.execute();
      ps.close();
    } catch (SQLException e) {
      if (e.getMessage().contains("duplicate key value violates")) {
        throw new NonUniqueFieldException("Le username existe déjà");
      }
    }
  }
}
