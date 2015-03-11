package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * IEntityFactory implementation.
 *
 * @author Nicolas Fischer
 */
final class EntityFactory implements IEntityFactory {

  @Override
  public IUser createUser(String username, IHashHolderDto password, String lastName,
      String firstName, String email, IUserDto.Role role) {
    return new User(username, password, lastName, firstName, email, role);
  }

  @Override
  public IUser createUser(int id, int version, String username, IHashHolderDto password,
      String lastName, String firstName, String email, IUserDto.Role role,
      LocalDateTime registerDate) {

    return new User(id, version, username, password, lastName, firstName, email, registerDate, role);
  }

  @Override
  public IContact createContact(Company company, String email, String firstName, String lastName,
      String phone) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IContact createContact(int idContact, int version, Company company, String email,
      String firstName, String lastName, String phone) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public ICompany createCompany(IUserDto creator, String name, String address_street,
      String addressNum, String addressMailbox, String addressPostcode, String addressTown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompany createCompany(int idCompany, int version, IUserDto creator, String name,
      String address_street, String addressNum, String addressMailbox, String addressPostcode,
      String addressTown) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IBusinessDay createBusinessDay(LocalDateTime eventDate, String academicYear) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IBusinessDay createBusinessDay(int idBusinessDay, int version, LocalDateTime creationDate,
      LocalDateTime eventDate, String academicYear) {
    // TODO Auto-generated method stub
    return null;
  }

}
