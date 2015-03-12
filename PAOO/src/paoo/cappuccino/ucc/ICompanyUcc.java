package paoo.cappuccino.ucc;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IUserDto;

/**
 * Use case controller containing methods relative to a company as an entity.
 *
 * @author Laurent
 */
public interface ICompanyUcc {

  /**
   * Register a new Company in the database.
   * 
   * @param creator The user who add this company.
   * @param name The Company's name.
   * @param street The company's street address.
   * @param numAdress The company's number address.
   * @param mailBox The company's mailbox
   * @param postCode The company's post code.
   * @param town The company's town.
   * @return The new company's DTO.
   * 
   * @throws java.lang.IllegalArgumentException The company's name must be unique.
   */
  public ICompanyDto add(IUserDto creator, String name, String street, String numAdress,
      String mailBox, String postCode, String town);

  /**
   * Get the companies matching the criteria of research.
   * 
   * @param name The name searched.
   * @param postCode The post code of the area within the searched company is established.
   * @param town The town within the searched company is established.
   * @param street The street within the searched company is established.
   * @return A array of the companies'DTO matching the criteria.
   */
  public ICompanyDto[] getCompanies(String name, String postCode, String town, String street);
}
