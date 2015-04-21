package paoo.cappuccino.ihm.searchcompanies;

import paoo.cappuccino.ihm.util.BaseModel;


public class CompaniesSearchModel extends BaseModel {

  private String name;
  private String postCode;
  private String town;
  private String street;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    dispatchChangeEvent();
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
    dispatchChangeEvent();
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
    dispatchChangeEvent();
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
    dispatchChangeEvent();
  }
}
