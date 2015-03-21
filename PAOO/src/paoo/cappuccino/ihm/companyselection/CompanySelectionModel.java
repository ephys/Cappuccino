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
  boolean resetTable;


  public void setCompanyDto(ICompanyDto[] companyDto) {

    this.companyDto = companyDto;
    this.selectAll = false;
    this.resetTable = true;
    dispatchChangeEvent();

  }

  public ICompanyDto[] getCompanyDto() {

    return this.companyDto;
  }

  public void setSelectAll(boolean b) {

    this.selectAll = b;
    dispatchChangeEvent();
  }

  public boolean getSelectAll() {

    return this.selectAll;
  }

  public boolean getResetTable() {

    return this.resetTable;
  }

  public void setResetTable(boolean b) {

    this.resetTable = b;
  }


}
