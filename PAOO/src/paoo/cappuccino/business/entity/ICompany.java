package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;

/**
 * Interface containing business methods relative to the Company entity.
 *
 * @author Nicolas Fischer
 */
public interface ICompany extends ICompanyDto, IBaseEntity {

  /**
   * 
   * @param contact
   */
  void addContact(IContactDto contact);
}