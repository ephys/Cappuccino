package paoo.cappuccino.ucc.impl;

import paoo.cappuccino.business.dto.ICompanyDto;
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
  public IContactDto create(ICompanyDto company, String email, String firstName, String lastName,
      String phone) {
    ValidationUtil.ensureFilled(lastName, "lastName");
    ValidationUtil.ensureFilled(firstName, "firstName");
    ValidationUtil.ensureFilled(phone, "phone");
    ValidationUtil.ensureNotNull(company, "company");
    ValidationUtil.ensureNotNull(lastName, "lastName");
    ValidationUtil.ensureNotNull(firstName, "firstName");
    ValidationUtil.ensureNotNull(phone, "phone");
    StringUtils.isEmail(email);
    StringUtils.isEmpty(email);
    IContactDto dto = factory.createContact(company.getId(), email, firstName, lastName, phone);
    IContactDto returnedDto = dao.createContact(dto);
    return returnedDto;
  }


  @Override
  public boolean setMailInvalid(IContactDto dto) {
    IContact contact = (IContact) dto;
    contact.setEmailValid(false);
    dao.updateContact(contact);
    return true;
  }

  @Override
  public IContactDto[] searchContact(String firstName, String lastName) {
    // TODO Auto-generated method stub
    return null;
  }

}
