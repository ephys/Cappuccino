package paoo.cappuccino.ihm.attendence;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the new Company View/ViewController.
 *
 * @author Opsomer mathias
 */
public class AttendanceModel extends BaseModel {
  private ICompanyDto selectedCompany;
  private IBusinessDayDto selectedDay;

  // table

  public ICompanyDto getSelectedCompany() {
    return selectedCompany;
  }

  public void setSelectedCompany(ICompanyDto selectedCompany) {
    this.selectedCompany = selectedCompany;
    dispatchChangeEvent();
  }

  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }

  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;
    dispatchChangeEvent();
  }

}
