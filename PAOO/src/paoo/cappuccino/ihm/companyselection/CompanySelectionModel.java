package paoo.cappuccino.ihm.companyselection;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the company selection model Gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionModel extends BaseModel {

  boolean selectAll;
  boolean notDeselectAll;
  private ICompanyDto[] companyDto;

  public ICompanyDto[] getCompanyDto() {
    return companyDto;
  }

  public void setCompanyDto(ICompanyDto[] companyDto) {
    this.companyDto = companyDto;
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
