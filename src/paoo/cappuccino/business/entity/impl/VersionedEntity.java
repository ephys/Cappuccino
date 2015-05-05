package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IVersionedEntity;

class VersionedEntity implements IVersionedEntity {

  private int version;

  public VersionedEntity(int version) {
    this.version = version;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public int incrementVersion() {
    return ++this.version;
  }

}
