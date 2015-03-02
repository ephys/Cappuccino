package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IBaseDto;

public interface IBaseEntity extends IBaseDto {
  public void setVersion(int version);
}
