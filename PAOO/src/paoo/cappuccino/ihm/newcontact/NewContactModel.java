package paoo.cappuccino.ihm.newcontact;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IContactDto;
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

  private IContactDto contact;
  private ICompanyDto company;

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
  public void setErrors(String firstNameError, String lastNameError, String mailError) {
    this.firstNameError = firstNameError;
    this.lastNameError = lastNameError;
    this.mailError = mailError;

    dispatchChangeEvent();
  }

  public boolean hasError() {
    return firstNameError != null || mailError != null || lastNameError != null;
  }

  @Override
  public void init(Object[] data) {
    contact = null;
    company = null;
    if (data == null || data.length == 0) {
      return;
    }

    if (data[0] instanceof ICompanyDto) {
      setCompany((ICompanyDto) data[0]);
      return;
    }

    if (data[0] instanceof IContactDto) {
      contact = (IContactDto) data[0];
      return;
    }

    throw new IllegalArgumentException("Invalid initialization data");
  }

  public IContactDto getContact() {
    return contact;
  }

  public ICompanyDto getCompany() {
    return company;
  }

  public void setCompany(ICompanyDto company) {
    this.company = company;
  }

}
