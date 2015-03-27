package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.ICompanyDto;

public interface ICompanyDao {

  /**
   * Inserts a new company in the database.
   *
   * @param company The company to insert.
   * @return The company entity with its information updated from the database.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The company name is not unique.
   * @throws java.lang.IllegalArgumentException One of the fields failed to insert due to constraint
   *         violations.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  ICompanyDto createCompany(ICompanyDto company);

  /**
   * Update a company in the database
   *
   * @param company A company entity
   * @throws java.util.ConcurrentModificationException the entity version did not match or the
   *         entity has been deleted.
   * @throws java.lang.IllegalArgumentException the entity hasn't been inserted in the database yet.
   *         Or one of the fields failed to insert to to constraint violations.
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The name is not unique.
   * @throws paoo.cappuccino.util.exception.FatalException a database connection error occurred.
   */
  void updateCompany(ICompanyDto company);

  /**
   * Fetches the companies matching all the non-null parameters.
   *
   * @param name The name of the company, nullable.
   * @param postcode The postcode part of the address of the company, nullable.
   * @param street The street part of the address of the company, nullable.
   * @param town The town part of the address of the company, nullable.
   * @return Every companies matching the fields.
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  ICompanyDto[] searchCompanies(String name, String postcode, String street, String town);


  /**
   * Fetch all the company stored in the database
   *
   * @return All the company found in the database or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error.
   */
  ICompanyDto[] fetchAll();

  /**
   * Fetch all the invitable companies
   *
   * @return Every companies invitable or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error
   */
  ICompanyDto[] fetchInvitableCompanies();


  /**
   * Returns the company registered using the given id.
   */
  ICompanyDto fetchCompanyById(int id);

  /**
   * Fetch all the companies who have participate at the businessDay
   *
   * @param businessDayId The id of the business day
   * @return Every companies who was present or null if none was found
   * @throws paoo.cappuccino.util.exception.FatalException Database connection error
   */
  ICompanyDto[] fetchCompaniesByDay(int businessDayId);
}
