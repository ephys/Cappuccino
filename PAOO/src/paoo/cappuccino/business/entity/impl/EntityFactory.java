package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.IParticipation;
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

    return new User(id, version, username, password, lastName,
                    firstName, email, registerDate, role);
  }

  @Override
  public IContact createContact(int companyId, String email, String firstName, String lastName,
      String phone) {
    return new Contact(companyId, email, true, firstName, lastName, phone);
  }

  @Override
  public IContact createContact(int id, int version, int companyId, String email,
      boolean emailValid, String firstName, String lastName, String phone) {
    return new Contact(id, version, companyId, email, emailValid, firstName, lastName, phone);
  }

  @Override
  public ICompany createCompany(int creatorId, String name, String addressStreet,
      String addressNum, String addressMailbox, String addressPostcode, String addressTown) {
    return new Company(name, addressStreet, addressNum, addressMailbox, addressPostcode,
        addressTown, creatorId);
  }

  @Override
  public ICompany createCompany(int id, int version, int creatorId, String name,
      String addressStreet, String addressNum, String addressMailbox, String addressPostcode,
      String addressTown, LocalDateTime registerDate) {
    return new Company(id, version, name, registerDate, addressStreet, addressNum, addressMailbox,
        addressPostcode, addressTown, creatorId);
  }

  @Override
  public IBusinessDay createBusinessDay(LocalDateTime eventDate) {
    return new BusinessDayEntity(eventDate);
  }

  @Override //TODO creation date
  public IBusinessDay createBusinessDay(int id, int version, LocalDateTime eventDate) {
    return new BusinessDayEntity(id, version, eventDate);
  }

  @Override
  public IParticipation createParticipation(int companyId, int businessDayId) {
    return new ParticipationEntity(businessDayId, companyId);
  }

  @Override
  public IParticipation createParticipation(int companyId, int businessDayId, int version,
      State state) {
    return new ParticipationEntity(version, state, businessDayId, companyId);
  }
}
