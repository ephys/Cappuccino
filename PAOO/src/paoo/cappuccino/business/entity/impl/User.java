package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;

/**
 * Classe qui reprend toutes les informations d'un utilisateur
 */
final class User extends BaseEntity implements IUser {
  private String username;
  private byte[] password;
  private String lastName;
  private String firstName;
  private String email;
  private LocalDateTime registerDate;
  private Role role;

  public User(int id, String username, byte[] password, String lastName, String firstName,
      String email, LocalDateTime registerDate, Role role, int version) {
    super(id, version);

    this.username = username;
    this.password = password;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.registerDate = registerDate;
    this.role = role;
  }

  @Override
  public String getUsername() {
    return username;
  }



  @Override
  public byte[] getPassword() {
    return password;
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
    return (LocalDateTime) registerDate;
  }



  @Override
  public Role getRole() {
    return role;
  }



  @Override
  public boolean authenticatePassword(final byte[] newPassword) {
    return this.password.equals(newPassword);
  }

  @Override
  public int getVersion() {
    return this.getVersion();
  }


}
