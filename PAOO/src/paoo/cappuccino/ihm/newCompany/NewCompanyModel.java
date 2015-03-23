package paoo.cappuccino.ihm.newCompany;

import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model for the new Company View/ViewController.
 *
 * @author Opsomer mathias
 */
public class NewCompanyModel extends BaseModel {

  private String nameError;
  private String streetError;
  private String cityError;
  private String boxError;
  private String numerError;
  private String postCodeError;

  public String getCompanyNameError() {
    return nameError;
  }

  public String getStreetError() {
    return streetError;
  }

  public String getCityError() {
    return cityError;
  }

  public String getBoxError() {
    return boxError;
  }

  public String getNumerError() {
    return numerError;
  }

  public String getPostCodeError() {
    return postCodeError;
  }

  public void setCompanyNameError(String error) {
    nameError = error;
    dispatchChangeEvent();
  }

}
