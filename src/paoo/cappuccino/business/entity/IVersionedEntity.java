package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IVersionnedDto;

public interface IVersionedEntity extends IVersionnedDto {

  /**
   * Increments and returns the entity version.
   */
  int incrementVersion();
}
