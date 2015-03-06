package paoo.cappuccino.dal.impl;


import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalBackend;
import paoo.cappuccino.dal.dao.IUserDao;
import paoo.cappuccino.dal.exception.ConnectionException;
import paoo.cappuccino.dal.exception.DaoException;
import paoo.cappuccino.util.hasher.StringHasher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implements de IUserDao
 *
 * @author Kevin Bavay
 */
public class UserDao implements IUserDao {

    @Inject private IEntityFactory entityFactory;
    @Inject private IDalBackend iDalBackend;

    @Override public IUser createUser(IUserDto user) {
        String query =
            "INSERT INTO businessDays.users VALUES ( DEFAULT,"
                + user.getRole().toString()
                + "," + StringHasher.INSTANCE.serialize(user.getPassword())
                + "," + user.getEmail() + "," +user.getUsername()+ "," + user.getFirstName() + "," + user.getLastName()
                + ",DEFAULT, DEFAULT);";
        PreparedStatement ps;
        try {
            ps = iDalBackend.fetchPrepardedStatement(query);
            ps.execute();
            ps.close();
        } catch (ConnectionException e) {
            throw new DaoException("Could not create the user", e);
        } catch (SQLException e) {
            throw new DaoException("Database Error", e);
        }
        return fetchUserByUsername(user.getUsername());
    }

    @Override public IUser fetchUserByUsername(String username) {
        String query = "SELECT * FROM businessDays.users WHERE username LIKE '" + username + "'";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = iDalBackend.fetchPrepardedStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                return entityFactory.createUser(rs.getInt("users_id"), rs.getInt("version"),
                    rs.getString("username"),
                    StringHasher.INSTANCE.unserialize(rs.getString("password")),
                    rs.getString("last_name"), rs.getString("first_name"), rs.getString("email"),
                    IUserDto.Role.valueOf(rs.getString("role")),
                    LocalDateTime.parse(rs.getString("register_date")));
                //TODO fermer les ps et rs
            } else {
                throw new DaoException("No user return");
            }
        } catch (ConnectionException e) {
            throw new DaoException("Could not update the user", e);
        } catch (SQLException e) {
            throw new DaoException("Database error", e);
        }
    }


    @Override public void updateUser(IUser user) {
        String query =
            "UPDATE businessDays.users SET username, password, email, first_name, last_name = ("
                + user.getUsername() + "," + StringHasher.INSTANCE.serialize(user.getPassword())
                + "," + user.getEmail() + "," + user.getFirstName() + "," + user.getLastName()
                + " )  WHERE users_id LIKE '" + user.getId() + "'";
        PreparedStatement ps;
        try {
            ps = iDalBackend.fetchPrepardedStatement(query);
            ps.execute();
            ps.close();
        } catch (ConnectionException e) {
            throw new DaoException("Could not update the user", e);
        } catch (SQLException e) {
            throw new DaoException("Database error", e);
        }
    }
}

