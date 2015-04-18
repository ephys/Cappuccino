package paoo.cappuccino.ihm.newcontact;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the new contact screen.
 *
 * @author Opsomer mathias
 */
public class NewContactModel extends BaseModel implements Initializable {

  private String firstNameError;
  private String lastNameError;
  private String mailError;
  private ICompanyDto companyDto;
  private String companyError;

  public String getFirstNameError() {
    return firstNameError;
  }

  public String getLastNameError() {
    return lastNameError;
  }

  public String getMailError() {
    return mailError;
  }

  /**
   * Sets the errors relative to the fields of the contact creation form.
   */
  public void setErrors(String firstNameError, String lastNameError,
                        String mailError, String companyError) {
    this.firstNameError = firstNameError;
    this.lastNameError = lastNameError;
    this.mailError = mailError;
    this.companyError = companyError;

    dispatchChangeEvent();
  }

  public boolean hasError() {
    return firstNameError != null || mailError != null
           || lastNameError != null || companyError != null;
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

  public ICompanyDto getCompanyDto() {
    return companyDto;
  }

  public void setCompanyDto(ICompanyDto companyDto) {
    this.companyDto = companyDto;
  }

  public String getCompanyError() {
    return companyError;
  }
}
