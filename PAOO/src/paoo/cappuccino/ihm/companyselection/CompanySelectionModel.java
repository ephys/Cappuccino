package paoo.cappuccino.ihm.companyselection;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the company selection model Gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionModel extends BaseModel {


  private ICompanyDto[] companyDto;
  boolean selectAll;
  boolean notDeselectAll;


  public void setCompanyDto(ICompanyDto[] companyDto) {

    this.companyDto = companyDto;
    this.selectAll = false;
    dispatchChangeEvent();

  }

  public ICompanyDto[] getCompanyDto() {

    return this.companyDto;
  }

  public void setSelectAll(boolean b) {


    this.selectAll = b;
    if (!notDeselectAll)
      dispatchChangeEvent();
  }

  public boolean getSelectAll() {

    return this.selectAll;
  }

  public void setNotDeselectAll(boolean b) {

    this.notDeselectAll = b;
  }

  public boolean getNotDeselectAll() {

    return this.notDeselectAll;
  }


}
