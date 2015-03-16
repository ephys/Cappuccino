package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.ICompany;

/**
 * Class implementing the ICompany entity.
 *
 * @author Nicolas Fischer
 */
final class CompanyEntity extends BaseEntity implements ICompany {

  private final String name;
  private final LocalDateTime registerDate;
  private final int creatorId;
  private String addressStreet;
  private String addressNum;
  private String addressMailbox;
  private String addressPostcode;
  private String addressTown;

  public CompanyEntity(String name, String addressStreet, String addressNum, String addressMailbox,
      String addressPostcode, String addressTown, int creatorId) {
    this(-1, 0, name, LocalDateTime.now(), addressStreet, addressNum, addressMailbox,
        addressPostcode, addressTown, creatorId);
  }

  public CompanyEntity(int id, int version, String name, LocalDateTime registerDate,
      String addressStreet, String addressNum, String addressMailbox, String addressPostcode,
      String addressTown, int creatorId) {
    super(id, version);
    this.name = name;
    this.registerDate = registerDate;
    this.addressStreet = addressStreet;
    this.addressNum = addressNum;
    this.addressMailbox = addressMailbox;
    this.addressPostcode = addressPostcode;
    this.addressTown = addressTown;
    this.creatorId = creatorId;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public LocalDateTime getRegisterDate() {
    return this.registerDate;
  }

  @Override
  public String getAddressStreet() {
    return this.addressStreet;
  }

  @Override
  public String getAddressNum() {
    return this.addressNum;
  }

  @Override
  public String getAddressMailbox() {
    return this.addressMailbox;
  }

  @Override
  public String getAddressPostcode() {
    return this.addressPostcode;
  }

  @Override
  public String getAddressTown() {
    return this.addressTown;
  }

  @Override
  public int getCreator() {
    return this.creatorId;
  }
}
