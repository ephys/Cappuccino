package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.entity.IBaseEntity;

/**
 * Classe qui implemente les methodes communes a chaque entité
 */
class BaseEntity implements IBaseEntity {
  private final int id;
  private int version;

  /**
   * Constructeur de la classe BizObject
   */
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
  public void setVersion(int version) {
    this.version = version;
  }
}
