package paoo.cappuccino.ihm.newcompany;

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
  }

  public void setNameError(String nameError) {
    this.nameError = nameError;
  }

  public void setStreetError(String streetError) {
    this.streetError = streetError;
  }

  public void setCityError(String cityError) {
    this.cityError = cityError;
  }

  public void setBoxError(String boxError) {
    this.boxError = boxError;
  }

  public void setNumerError(String numerError) {
    this.numerError = numerError;
  }

  public void setPostCodeError(String postCodeError) {
    this.postCodeError = postCodeError;
  }

  public boolean hasError() {
    if (nameError != null || boxError != null || cityError != null || numerError != null
        || postCodeError != null || streetError != null) {
      dispatchChangeEvent();
      return true;
    }
    return false;
  }

  public void clearError() {
    boxError = null;
    cityError = null;
    nameError = null;
    numerError = null;
    postCodeError = null;
    streetError = null;
    dispatchChangeEvent();
  }

}
