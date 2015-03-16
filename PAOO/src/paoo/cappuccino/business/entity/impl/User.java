package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Class implementing the IUser entity.
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

  public User(String username, IHashHolderDto password, String lastName, String firstName,
      String email, Role role) {

    this(-1, 0, username, password, lastName, firstName, email, LocalDateTime.now(), role);
  }

  public User(int id, int version, String username, IHashHolderDto password, String lastName,
      String firstName, String email, LocalDateTime registerDate, Role role) {

    super(id, version);

    this.password = password;
    this.registerDate = registerDate;
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
  public void setPassword(IHashHolderDto password) {
    this.password = password;
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
