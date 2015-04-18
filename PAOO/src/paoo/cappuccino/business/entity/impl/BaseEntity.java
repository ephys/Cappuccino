package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IBaseEntity;

/**
 * Class implementing methods shared by every other entity.
 *
 * @author Nicolas Fischer
 */
abstract class BaseEntity implements IBaseEntity {

  private final int id;
  private int version;

  public BaseEntity(int id) {
    this.id = id;
  }

  public BaseEntity(int id, int version) {
    this(id);
    this.version = version;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public int incrementVersion() {
    return ++this.version;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
