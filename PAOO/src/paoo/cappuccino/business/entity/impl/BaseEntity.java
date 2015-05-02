package paoo.cappuccino.business.entity.impl;

import paoo.cappuccino.business.dto.IBaseDto;
import paoo.cappuccino.business.entity.IVersionedEntity;

/**
 * Class implementing methods shared by every other entity.
 *
 * @author Nicolas Fischer
 */
abstract class BaseEntity extends VersionedEntity implements IBaseDto, IVersionedEntity {

  private final int id;

  public BaseEntity(int id, int version) {
    super(version);
    this.id = id;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
