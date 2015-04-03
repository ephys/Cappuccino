package paoo.cappuccino.dal.mock;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.dao.IContactDao;

/**
 * Mock implementation of the Contact DAO.
 */
class MockContactDao implements IContactDao {
  private final List<IContact> contactList = new ArrayList<>();
  private final IEntityFactory factory;

  /**
   * Creates a new mock instance of the contact dao.
   */
  @Inject
  public MockContactDao(IEntityFactory factory) {
    this.factory = factory;

    createContact(factory.createContact(1, "LIF@gmail.com", "Paul", "Pierre", "02/124.23.18"));
    createContact(factory.createContact(1, "JeanLouis@gmail.com", "Louis", "Jean", "02/122.32.18"));
    createContact(factory.createContact(1, "AlfredChandleur@gmail.com", "Chandleur",
                                        "Alfred", "02/237.42.79"));
    createContact(factory.createContact(1, "Contact@gmail.com", "ContactFirstname",
                                        "ContactLasttname", "0488899999"));
  }

  @Override
  public IContactDto createContact(final IContactDto contact) {
    IContact contactEntity =
        factory.createContact(contactList.size() + 1, 1, contact.getCompany(), contact.getEmail(),
            contact.isEmailValid(), contact.getFirstName(), contact.getLastName(),
            contact.getPhone());

    contactList.add(contactEntity);
    return contactEntity;
  }

  @Override
  public void updateContact(IContactDto contact) {
    if (contactList.size() > contact.getId()
        || contactList.get(contact.getId() - 1).getVersion() != contact.getVersion()) {
      throw new ConcurrentModificationException();
    }

    IContact contactEntity = factory.createContact(contact.getId(), contact.getVersion() + 1,
                                                   contact.getCompany(), contact.getEmail(),
                                                   contact.isEmailValid(), contact.getFirstName(),
                                                   contact.getLastName(), contact.getPhone());

    contactList.set(contact.getId() - 1, contactEntity);
  }

  @Override
  public IContactDto[] fetchContactByName(String firstName, String lastName) {
    List<IContact> toReturn = contactList.stream()
        .filter(searched ->
                    (firstName == null || searched.getFirstName().equalsIgnoreCase(firstName))
                    && (lastName == null || searched.getLastName().equalsIgnoreCase(lastName)))
        .collect(Collectors.toList());

    return toReturn.toArray(new IContactDto[toReturn.size()]);
  }

  @Override
  public IContactDto[] fetchContactsByCompany(int companyId) {
    List<IContact> toReturn = contactList.stream()
        .filter(searchee -> searchee.getCompany() == companyId)
        .collect(Collectors.toList());

    return toReturn.toArray(new IContactDto[toReturn.size()]);
  }
}
