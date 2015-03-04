package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.StringHasher;

/**
 * Class implementing the IUser entity
 *
 * @author Nicolas Fischer
 */
final class User extends BaseEntity implements IUser {

  private final String username;
  private IHashHolderDto password;
  private String lastName;
  private String firstName;
  private String email;
  private LocalDateTime registerDate;
  private Role role;

  public User(String username, String password, String lastName, String firstName,
              String email, Role role) {

    this(-1, 0, username, lastName, firstName, email, role);
    setPassword(password);
    this.registerDate = LocalDateTime.now();
  }

  public User(int id, int version, String username, IHashHolderDto password, String lastName,
              String firstName, String email, LocalDateTime registerDate, Role role) {

    this(id, version, username, lastName, firstName, email, role);

    this.password = password;
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
  public String getUsername() {
    return username;
  }

  @Override
  public IHashHolderDto getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = StringHasher.INSTANCE.hash(password);
  }

  @Override
  public boolean upgradePassword(String password) {
    IHashHolderDto hashHolder = StringHasher.INSTANCE.reHash(password, this.password);

    if (hashHolder != null) {
      this.password = hashHolder;
      return true;
    }

    return false;
  }

  @Override
  public boolean isPassword(final String passwordAttempt) {
    return StringHasher.INSTANCE.matchHash(passwordAttempt, this.password);
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
