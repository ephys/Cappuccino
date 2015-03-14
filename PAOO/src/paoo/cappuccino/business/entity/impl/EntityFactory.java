package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IParticipationDto;
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

    return new User(id, version, username, password, lastName, firstName, email, registerDate,
                    role);
  }

  @Override
  public IContact createContact(int companyId, String email, String firstName, String lastName,
                                String phone) {
    return null;
  }

  @Override
  public IContact createContact(int id, int version, int companyId, String email,
                                boolean emailValid, String firstName, String lastName,
                                String phone) {
    return null;
  }

  @Override
  public ICompany createCompany(int creatorId, String name, String addressStreet, String addressNum,
                                String addressMailbox, String addressPostcode, String addressTown) {
    return null;
  }

  @Override
  public ICompany createCompany(int id, int version, int creatorId, String name,
                                String addressStreet, String addressNum, String addressMailbox,
                                String addressPostcode, String addressTown) {
    return null;
  }

  @Override
  public IBusinessDay createBusinessDay(LocalDateTime eventDate) {
    return null;
  }

  @Override
  public IBusinessDay createBusinessDay(int id, int version, LocalDateTime creationDate,
                                        LocalDateTime eventDate) {
    return null;
  }

  @Override
  public IParticipation createParticipation(int companyId, int businessDayId) {
    return null;
  }

  @Override
  public IParticipation createParticipation(int companyId, int businessDayId, int version,
                                            IParticipationDto.State state, boolean cancelled) {
    return null;
  }
}
