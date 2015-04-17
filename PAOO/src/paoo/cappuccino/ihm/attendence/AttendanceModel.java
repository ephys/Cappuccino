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
  private boolean notDeselectAll;
  private boolean selectAll;

  // table

  public ICompanyDto getSelectedCompany() {
    return selectedCompany;
  }

  public void setSelectedCompany(ICompanyDto selectedCompany) {
    this.selectedCompany = selectedCompany;
    selectAll = false;
    dispatchChangeEvent();
  }

  public IBusinessDayDto getSelectedDay() {
    return selectedDay;
  }

  public void setSelectedDay(IBusinessDayDto selectedDay) {
    this.selectedDay = selectedDay;
    selectAll = false;
    dispatchChangeEvent();
  }

  public boolean isSelectAll() {
    return selectAll;
  }

  public void setSelectAll(boolean b) {

    selectAll = b;
    if (!notDeselectAll) {
      dispatchChangeEvent();
    }
  }

  public boolean isNotDeselectAll() {
    return notDeselectAll;
  }

  public void setNotDeselectAll(boolean b) {
    notDeselectAll = b;
  }



}
