package paoo.cappuccino.ucc.impl;

import java.util.List;

import paoo.cappuccino.business.dto.IContactDto;
import paoo.cappuccino.business.entity.IContact;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IContactDao;
import paoo.cappuccino.ucc.IContactUcc;
import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.ValidationUtil;

class ContactUcc implements IContactUcc {

  private final IEntityFactory factory;
  private final IContactDao dao;

  @Inject
  public ContactUcc(IEntityFactory entityFactory, IDalService dalService, IContactDao contactDao) {
    this.factory = entityFactory;
    this.dao = contactDao;
  }

  @Override
  public IContactDto create(int companyId, String email, String firstName, String lastName,
                            String phone) {
    ValidationUtil.ensureFilled(lastName, "lastName");
    ValidationUtil.ensureFilled(firstName, "firstName");

    if (StringUtils.isEmpty(phone)) {
      phone = null;
    }

    if (StringUtils.isEmpty(email)) {
      email = null;
    } else if (!StringUtils.isEmail(email)) {
      throw new IllegalArgumentException("Invalid email format for " + email);
    }

    return dao.createContact(factory.createContact(companyId, email, firstName, lastName, phone));
  }

  @Override
  public boolean setMailInvalid(IContactDto contactDto) {
    ValidationUtil.ensureNotNull(contactDto, "contactDto");
    if (StringUtils.isEmpty(contactDto.getEmail()) || !contactDto.isEmailValid()) {
      return false;
    }

    IContact contact = convertContact(contactDto);
    contact.setEmailValid(false);
    dao.updateContact(contact);

    return true;
  }

  @Override
  public List<IContactDto> searchContact(String firstName, String lastName) {
    if (StringUtils.isEmpty(firstName)) {
      firstName = null;
    }

    if (StringUtils.isEmpty(lastName)) {
      lastName = null;
    }

    return dao.fetchContactByName(firstName, lastName);
  }

  @Override
  public List<IContactDto> getContactByCompany(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("invalid id");
    }
    return dao.fetchContactsByCompany(id);
  }

  private IContact convertContact(IContactDto dto) {
    if (dto instanceof IContact) {
      return (IContact) dto;
    } else {
      return factory.createContact(dto.getId(), dto.getVersion(), dto.getCompany(), dto.getEmail(),
          dto.isEmailValid(), dto.getFirstName(), dto.getLastName(), dto.getPhone());
    }
  }
}
