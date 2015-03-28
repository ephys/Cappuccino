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
   * Creates a new company and persists it in the database.
   *
   * @param creator The user who add this company.
   * @param name The name of the company.
   * @param street The street of the company's address.
   * @param numAdress The number of the company's address.
   * @param mailBox The mailbox of the company's address, nullable.
   * @param postCode The post code of the company's address.
   * @param town The town of the company's address.
   * @return The new company's DTO.
   * @throws java.lang.IllegalArgumentException The company name is not unique or a field is empty.
   */
  public ICompanyDto create(IUserDto creator, String name, String street, String numAdress,
      String mailBox, String postCode, String town);

  /**
   * Get the companies matching the criteria of research.
   *
   * @param name The name searched.
   * @param postCode The post code of the area within the searched company is established.
   * @param town The town within the searched company is established.
   * @param street The street within the searched company is established.
   * @return An array of the companies'DTO matching the criteria.
   */
  public ICompanyDto[] searchCompanies(String name, String postCode, String town, String street);

  /**
   * Returns all the companies that can be invite to a business days.
   * 
   * @return An array of CompanyDto which can be currently invited.
   */
  public ICompanyDto[] getInvitableCompanies();

  /**
   * Returns all the company existing in the database.
   * 
   * @return An array of all the CompanyDto.
   */
  public ICompanyDto[] getAllCompanies();

  /**
   * Returns the company matching the Id given.
   * 
   * @param company The id of the researched company.
   * @return An ICompanyDto of the company having the id given.
   */
  public ICompanyDto getCompanyById(int company);
}
