package paoo.cappuccino.ihm.accueil;

import javax.swing.ComboBoxModel;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.ucc.ICompanyUcc;

/**
 * Model for the Login View/ViewController.
 *
 * @author Opsomer Mathias
 */
public class AccueilModel extends BaseModel {

  private final ICompanyUcc companyUcc;
  private final IBusinessDayUcc businessDayUcc;

  @Inject
  public AccueilModel(ICompanyUcc companyUcc,
      IBusinessDayUcc businessDayUcc) {
    this.companyUcc = companyUcc;
    this.businessDayUcc = businessDayUcc;
  }

  /**
   * get all the business day
   * 
   * @return a table with all the business day
   */
  public IBusinessDayDto[] getAllDays() {
    return businessDayUcc.getBusinessDays();
  }

  /**
   * get all companies suscribed for a day
   * 
   * @return table with all companies concerned
   */
  public ICompanyDto[] getCompanies(IBusinessDayDto day) {
    // return companyUcc.getCompaniesForADay(day);
    return null;
  }

  /**
   * unsubscribe a company for the selected day
   * 
   * @param c the company to unsubscribe
   */
  public void removeCompany(ICompanyDto c) {
    // TODO Auto-generated method stub

  }

  /**
   * get all the state possible for a company
   * 
   * @param c the company
   * @return a table with all the state possible
   */
  public ComboBoxModel<State> getPossibleState(ICompanyDto c) {
    // companyUcc.getPossibleState(c);
    return null;
  }
}