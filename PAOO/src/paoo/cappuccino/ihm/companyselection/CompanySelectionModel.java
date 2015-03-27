package paoo.cappuccino.ihm.companyselection;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the company selection model Gui.
 *
 * @author Maduka Junior
 */
public class
    CompanySelectionModel extends BaseModel {


  private ICompanyDto[] companyDto;
  boolean selectAll;
  boolean notDeselectAll;


  public void setCompanyDto(ICompanyDto[] companyDto) {

    this.companyDto = companyDto;
    selectAll = false;
    dispatchChangeEvent();

  }

  public ICompanyDto[] getCompanyDto() {

    return companyDto;
  }

  public void setSelectAll(boolean b) {


    selectAll = b;
    if (!notDeselectAll)
      dispatchChangeEvent();
  }

  public boolean getSelectAll() {

    return selectAll;
  }

  public void setNotDeselectAll(boolean b) {

    notDeselectAll = b;
  }

  public boolean getNotDeselectAll() {

    return notDeselectAll;
  }


}
