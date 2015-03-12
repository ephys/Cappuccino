package paoo.cappuccino.ucc;

import java.util.Date;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;

/**
 * Use case controller containing methods relative to a Business Day as an entity.
 *
 * @author Laurent
 */
public interface IBusinessDayUcc {

  /**
   * Register à new Business Day In the database.
   * 
   * @param evenDate The exact date of the even.
   * @param academicYear The year of the even.
   * @return The new Business Day DTO
   * 
   * @throws java.lang.IllegalArgumentException The academic year must be unique.
   */
  public IBusinessDayDto add(Date evenDate, int academicYear);

  /**
   * Add a company to the business day with a state set as INVITED.
   * 
   * @param company The company added.
   * @return The company's DTO if successfully added.
   */
  public ICompanyDto addCompany(ICompanyDto company);

  /**
   * Change a company state in the database.
   * 
   * @param company The company which need changes.
   * @param state The new state of the company.
   * @return The company's DTO if the change is a success.
   */
  public ICompanyDto switchState(ICompanyDto company, String state);

  /**
   * Return the Business Day's DTO of the researched year.
   * 
   * @param firstName The year of the business day searched.
   * @return The business Day's DTO researched.
   * @throws java.lang.IllegalArgumentException No BusinessDay known at this year.
   */
  public IBusinessDayDto getBusinessDay(int academicYear);

  /**
   * Return a table with all the Business Day's DTO .
   * 
   * @return A table with the business Day's DTO existing in the database.
   */
  public IBusinessDayDto[] getBusinessDays();

  /**
   * Get all the participating companies of a known Business Days.
   * 
   * @param businessDays The business day for which the companies are needed.
   * @return A table of companies DTO participating to the business Day wanted.
   */
  public ICompanyDto[] getParticipations(IBusinessDayDto businessDays);
}
