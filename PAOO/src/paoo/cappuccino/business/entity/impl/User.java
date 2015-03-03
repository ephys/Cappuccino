package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;

/**
 * Class implementing the IUser entity
 *
 * @author Nicolas Fischer
 */
final class User extends BaseEntity implements IUser {

  private final String username;
  private byte[] passwordHash;
  private byte[] passwordSalt;
  private String lastName;
  private String firstName;
  private String email;
  private LocalDateTime registerDate;
  private Role role;

  public User(String username, String password, String lastName, String firstName,
              String email, Role role) {

    this(-1, 0, username, lastName, firstName, email, role);
    setPassword(password);
  }

  public User(int id, int version, String username, byte[] passwordHash, byte[] salt,
              String lastName,
              String firstName, String email, LocalDateTime registerDate, Role role) {

    this(id, version, username, lastName, firstName, email, role);

    this.passwordHash = passwordHash;
    this.passwordSalt = salt;
    this.registerDate = registerDate;
  }

  private User(int id, int version, String username, String lastName, String firstName,
               String email, Role role) {

    super(id, version);

    this.username = username;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.role = role;
  }

  @Override
  public void setPassword(String password) {
    // TODO: if no salt is defined generate one. Then hash the password
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public byte[] getPasswordHash() {
    return passwordHash;
  }

  @Override
  public byte[] getPasswordSalt() {
    return passwordSalt;
  }

  @Override
  public boolean isPassword(final String password) {
    // TODO: hash that password using this user's salt, check the resulting byte array

    /*
    if (otherPassword.length != this.passwordHash.length) {
      return false;
    }

    for (int i = 0; i < otherPassword.length; i++) {
      if (otherPassword[i] != this.passwordHash[i]) {
        return false;
      }
    }

    return true;*/

    throw new UnsupportedOperationException("nyi");
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public LocalDateTime getRegisterDate() {
    return registerDate;
  }

  @Override
  public Role getRole() {
    return role;
  }
}
