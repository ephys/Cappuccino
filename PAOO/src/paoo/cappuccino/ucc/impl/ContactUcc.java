package paoo.cappuccino.ucc.impl;

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
    } else {
      if (!StringUtils.isEmail(email)) {
        throw new IllegalArgumentException("Invalid email format for " + email);
      }
    }

    return dao.createContact(factory.createContact(companyId, email, firstName, lastName, phone));
  }

  @Override
  public boolean setMailInvalid(IContactDto dto) {
    if (StringUtils.isEmpty(dto.getEmail()) || !dto.isEmailValid()) {
      return false;
    }

    IContact contact;
    if (dto instanceof IContact) {
      contact = (IContact) dto;
    } else {
      contact =
          factory.createContact(dto.getId(), dto.getVersion(), dto.getCompany(), dto.getEmail(),
              dto.isEmailValid(), dto.getFirstName(), dto.getLastName(), dto.getPhone());
    }

    contact.setEmailValid(false);
    dao.updateContact(contact);

    return true;
  }

  @Override
  public IContactDto[] searchContact(String firstName, String lastName) {
    ValidationUtil.ensureNotNull(firstName, "firstName");
    ValidationUtil.ensureNotNull(lastName, "lastName");
    return dao.fetchContactByName(firstName, lastName);
  }

}
