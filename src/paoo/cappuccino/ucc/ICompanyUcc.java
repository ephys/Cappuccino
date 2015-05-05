package paoo.cappuccino.ucc;

import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
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
   * @return The created company.
   * @throws java.lang.IllegalArgumentException The company name is not unique or a non-nullable
   *         field is empty.
   */
  ICompanyDto create(IUserDto creator, String name, String street, String numAdress,
      String mailBox, String postCode, String town);

  /**
   * Searches companies based on their address.
   *
   * @param name The name searched. Nullable.
   * @param postCode The post code of the area within the searched company is established. Nullable.
   * @param town The town within the searched company is established. Nullable.
   * @param street The street within the searched company is established. Nullable.
   * @return The list of companies matching the criteria.
   */
  List<ICompanyDto> searchCompanies(String name, String postCode, String town, String street);

  /**
   * Returns the list of companies eligible to be invited for a business day.
   */
  List<ICompanyDto> getInvitableCompanies();

  /**
   * Returns the list of persisted companies.
   */
  List<ICompanyDto> getAllCompanies();

  /**
   * Returns the company matching the given identifier.
   *
   * @param company The id of the researched company.
   */
  ICompanyDto getCompanyById(int company);

  /**
   * <p>Returns the list of companies attending a given business day.
   * This return every company that have been registered for a business day and have not cancelled
   * nor declined their invitation (yet).</p>
   *
   * <p>You can use {@link IBusinessDayUcc#getParticipations(int)} to fetch every registered
   * participation for a given business day, even cancelled/denied ones.</p>
   *
   * @param dayId The identifier of the business day.
   */
  List<ICompanyDto> getCompaniesByDay(int dayId);

  /**
   * Returns the list of business days a company has attended (as in did not cancel nor decline
   * the invitation to the event).
   *
   * @param companyId The identifier of the company.
   */
  List<IParticipationDto> getCompanyParticipations(int companyId);
}
