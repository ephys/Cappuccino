package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IAttendance;
import paoo.cappuccino.business.entity.IBusinessDay;
import paoo.cappuccino.business.entity.ICompany;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.IParticipation;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

/**
 * IEntityFactory implementation.
 *
 * @author Nicolas Fischer
 */
final class EntityFactory implements IEntityFactory {

  private final IStringHasher stringHasher;

  @Inject
  public EntityFactory(IStringHasher stringHasher) {
    this.stringHasher = stringHasher;
  }

  @Override
  public IUser createUser(String username, char[] password,
                          String lastName, String firstName, String email, IUserDto.Role role) {
    return new UserEntity(stringHasher, username, password, lastName,
                          firstName, email, role);
  }

  @Override
  public IUser createUser(int id, int version, String username,
                          IHashHolderDto password, String lastName, String firstName,
                          String email, IUserDto.Role role, LocalDateTime registerDate) {

    return new UserEntity(stringHasher, id, version, username, password,
                          lastName, firstName, email, registerDate, role);
  }

  @Override
  public IContact createContact(int companyId, String email,
                                String firstName, String lastName, String phone) {
    return new ContactEntity(companyId, email, true, firstName, lastName,
                             phone);
  }

  @Override
  public IContact createContact(int id, int version, int companyId,
                                String email, boolean emailValid, String firstName, String lastName,
                                String phone) {
    return new ContactEntity(id, version, companyId, email, emailValid,
                             firstName, lastName, phone);
  }

  @Override
  public ICompany createCompany(int creatorId, String name,
                                String addressStreet, String addressNum, String addressMailbox,
                                String addressPostcode, String addressTown) {
    return new CompanyEntity(name, addressStreet, addressNum,
                             addressMailbox, addressPostcode, addressTown, creatorId);
  }

  @Override
  public ICompany createCompany(int id, int version, int creatorId,
                                String name, String addressStreet, String addressNum,
                                String addressMailbox, String addressPostcode, String addressTown,
                                LocalDateTime registerDate) {
    return new CompanyEntity(id, version, name, registerDate,
                             addressStreet, addressNum, addressMailbox, addressPostcode,
                             addressTown, creatorId);
  }

  @Override
  public IBusinessDay createBusinessDay(LocalDateTime eventDate) {
    return new BusinessDayEntity(eventDate);
  }

  @Override
  public IBusinessDay createBusinessDay(int id, int version,
                                        LocalDateTime eventDate, LocalDateTime creationDate) {
    return new BusinessDayEntity(id, version, eventDate, creationDate);
  }

  @Override
  public IParticipation createParticipation(int companyId,
                                            int businessDayId) {
    return new ParticipationEntity(businessDayId, companyId);
  }

  @Override
  public IParticipation createParticipation(int companyId,
                                            int businessDayId, boolean cancelled, int version,
                                            State state) {
    return new ParticipationEntity(version, state, cancelled,
                                   businessDayId, companyId);
  }

  @Override
  public IAttendance createAttendance(int companyId, int businessDayId,
                                      int contactId) {
    return new AttendanceEntity(businessDayId, companyId, contactId);
  }

  public IAttendance createAttendance(int companyId, int businessDayId,
                                      int contactId, boolean cancelled, int version) {
    return new AttendanceEntity(businessDayId, companyId, contactId,
                                cancelled, version);
  }
}
