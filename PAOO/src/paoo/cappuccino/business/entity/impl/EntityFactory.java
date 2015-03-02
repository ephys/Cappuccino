package paoo.cappuccino.business.entity.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;

/**
 * Implémentation de l'IEntityFactory
 */
final class EntityFactory implements IEntityFactory {

  @Override
  public IUser createUser(String username, byte[] password, String lastName, String firstName,
      String email, LocalDateTime registerDate, String role, int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IUser createUser(String username, byte[] password, String lastName, String firstName,
      String email, LocalDateTime registerDate, String role) {
    // TODO Auto-generated method stub
    return null;
  }
}
