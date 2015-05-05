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
  private String numError;
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

  public String getNumError() {
    return numError;
  }

  public String getPostCodeError() {
    return postCodeError;
  }

  /**
   * Sets the errors relative to the various fields of the company creation form.
   */
  public void setErrors(String nameError, String streetError, String cityError, String boxError,
                        String numError, String postCodeError) {
    this.nameError = nameError;
    this.streetError = streetError;
    this.cityError = cityError;
    this.boxError = boxError;
    this.numError = numError;
    this.postCodeError = postCodeError;

    dispatchChangeEvent();
  }

  /**
   * Returns Whether or there is an error in one of the fields.
   */
  public boolean hasError() {
    return nameError != null || boxError != null || cityError != null || numError != null
           || postCodeError != null || streetError != null;
  }
}
