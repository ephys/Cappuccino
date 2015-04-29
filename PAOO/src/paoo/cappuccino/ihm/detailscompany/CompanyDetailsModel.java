package paoo.cappuccino.ihm.detailscompany;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.util.BaseModel;

public class CompanyDetailsModel extends BaseModel implements Initializable {

  private ICompanyDto companyDto;

  public ICompanyDto getCompanyDto() {
    return companyDto;
  }

  @Override
  public void init(Object[] data) {
    if (data == null || data.length != 1) {
      return;
    }

    if (!(data[0] instanceof ICompanyDto)) {
      throw new IllegalArgumentException("Incorrect initialization data for CompanyDetailsModel");
    }

    companyDto = (ICompanyDto) data[0];

    dispatchChangeEvent();
  }
}
