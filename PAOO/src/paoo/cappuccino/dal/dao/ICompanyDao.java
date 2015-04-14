package paoo.cappuccino.dal.dao;

import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * Data access object used to fetch or persist companies.
 */
public interface ICompanyDao {

  /**
   * Inserts a new company in the database.
   *
   * @param company The company to insert.
   * @return The company entity with its information updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The company name is not unique.
   * @throws java.lang.IllegalArgumentException                    One of the fields failed to
   *                                                               insert due to constraint
   *                                                               violations.
   * @throws paoo.cappuccino.util.exception.FatalException         Database connection error.
   */
  ICompanyDto createCompany(ICompanyDto company);

  /**
   * Updates a company in the database.
   *
   * @param company A company entity.
   * @throws java.util.ConcurrentModificationException             The entity version did not match
   *                                                               or the entity has been deleted.
   * @throws java.lang.IllegalArgumentException                    One of the fields failed to
   *                                                               insert to to constraint
   *                                                               violations.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The name is not unique.
   * @throws paoo.cappuccino.util.exception.FatalException         Database connection error.
   */
  void updateCompany(ICompanyDto company);

  /**
   * Fetches the companies matching all the non-null parameters.
   *
   * @param name     The name of the company, nullable.
   * @param postcode The postcode part of the address of the company, nullable.
   * @param street   The street part of the address of the company, nullable.
   * @param town     The town part of the address of the company, nullable.
   * @return Every companies matching the fields.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<ICompanyDto> searchCompanies(String name, String postcode, String street, String town);


  /**
   * Fetches the list of persisted companies.
   *
   * @return The list of companies.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<ICompanyDto> fetchAll();

  /**
   * Fetches the list of companies having participated in at least one of the 4 previous years'
   * business days and which paid its participation or have been added during the current academic
   * year.
   *
   * @return The list of invitable companies.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  List<ICompanyDto> fetchInvitableCompanies();


  /**
   * Fetches the company matching a given id.
   *
   * @param id The id of the company
   * @return The id's company or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  ICompanyDto fetchCompanyById(int id);

  /**
   * Fetches the list of companies participating at a business day.
   *
   * @param businessDayId The id of the business day
   * @return Every companies who was present or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error
   */
  List<ICompanyDto> fetchCompaniesByDay(int businessDayId);
}
