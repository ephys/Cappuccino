package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * Class implementing the IUser entity.
 *
 * @author Nicolas Fischer
 */
final class UserEntity extends BaseEntity implements IUser {

  private final IStringHasher hasher;
  private final String username;
  private IHashHolderDto password;
  private String lastName;
  private String firstName;
  private String email;
  private LocalDateTime registerDate;
  private Role role;

  public UserEntity(IStringHasher hasher, String username, char[] password, String lastName,
                    String firstName, String email, Role role) {

    this(hasher, -1, 0, username, hasher.hash(password), lastName, firstName, email,
         LocalDateTime.now(), role);
  }

  public UserEntity(IStringHasher hasher, int id, int version, String username,
                    IHashHolderDto password, String lastName, String firstName, String email,
                    LocalDateTime registerDate, Role role) {

    super(id, version);
    this.hasher = hasher;

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
  public boolean updatePasswordHashAlgorithm(char[] plainPassword) {
    if (!hasher.isHashOutdated(password)) {
      return false;
    }

    setPassword(hasher.hash(plainPassword));

    return true;
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

  @Override
  public boolean isPassword(char[] password) {
    return hasher.matchHash(password, this.password);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof IUserDto && ((IUserDto) obj).getId() == this.getId();
  }
}
