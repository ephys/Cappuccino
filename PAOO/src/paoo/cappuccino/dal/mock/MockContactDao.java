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
    if (contactList.size() < contact.getId()) {
      throw new ConcurrentModificationException("contact not found");
    } else if (contactList.get(contact.getId() - 1).getVersion() != contact.getVersion()) {
      throw new ConcurrentModificationException("Version mismatch");
    }

    IContact contactEntity = factory.createContact(contact.getId(), contact.getVersion() + 1,
                                                   contact.getCompany(), contact.getEmail(),
                                                   contact.isEmailValid(), contact.getFirstName(),
                                                   contact.getLastName(), contact.getPhone());

    contactList.set(contact.getId() - 1, contactEntity);
    if (contact instanceof IContact) {
      ((IContact) contact).incrementVersion();
    }
  }

  @Override
  public List<IContactDto> fetchContactByName(String firstName, String lastName) {
    final String finalFirstName = firstName == null ? null : firstName.toLowerCase();
    final String finalLastName = lastName == null ? null : lastName.toLowerCase();

    return contactList.stream()
        .filter(searched ->
                    (finalFirstName == null || searched.getFirstName().toLowerCase()
                        .contains(finalFirstName))
                    && (finalLastName == null || searched.getLastName().toLowerCase()
                        .contains(finalLastName)))
        .collect(Collectors.toList());
  }

  @Override
  public List<IContactDto> fetchContactsByCompany(int companyId) {
    return contactList.stream()
        .filter(searched -> searched.getCompany() == companyId)
        .collect(Collectors.toList());
  }
}
