package paoo.cappuccino.ihm.companySelection;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.util.BaseModel;

public class CompanySelectionModel extends BaseModel {


  ICompanyDto[] companyDto;
  boolean selectAll;


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
    dispatchChangeEvent();
  }

  public boolean getSelectAll() {

    return this.selectAll;
  }


}
