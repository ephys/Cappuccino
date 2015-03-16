package paoo.cappuccino.dal.dao;

import paoo.cappuccino.business.dto.ICompanyDto;

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
   * @throws paoo.cappuccino.dal.exception.FatalException     Database connection error.
   */
  ICompanyDto createCompany(ICompanyDto company);

  void updateCompany(ICompanyDto company);

  /**
   * Fetches the companies matching all the non-null parameters.
   *
   * @param name     The name of the company, nullable.
   * @param postcode The postcode part of the address of the company, nullable.
   * @param street   The street part of the address of the company, nullable.
   * @param town     The town part of the address of the company, nullable.
   * @return Every companies matching the fields.
   * @throws paoo.cappuccino.dal.exception.FatalException Database connection error.
   */
  ICompanyDto[] searchCompanies(String name, String postcode, String street, String town);

  ICompanyDto[] fetchAll();

  ICompanyDto[] fetchInvitableCompanies();

  ICompanyDto[] fetchCompaniesByDay(int businessDayId);
}
