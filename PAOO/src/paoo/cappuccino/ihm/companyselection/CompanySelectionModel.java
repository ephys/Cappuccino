package paoo.cappuccino.ihm.companyselection;

import java.util.List;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the company selection model Gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionModel extends BaseModel implements Initializable {

  boolean selectAll;
  boolean notDeselectAll;
  private List<ICompanyDto> companyDto;

  public List<ICompanyDto> getCompanyDto() {
    return companyDto;
  }

  public void setCompanyDto(List<ICompanyDto> companyDto) {
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


  @Override
  public void init(Object[] data) {
    // TODO
  }
}
