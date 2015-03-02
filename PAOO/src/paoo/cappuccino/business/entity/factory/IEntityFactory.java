package paoo.cappuccino.business.entity.factory;

import java.time.LocalDateTime;

import paoo.cappuccino.business.entity.IUser;

/**
 * 
 * Interface qui permet de cr�er de nouvelles classes Biz vide et permet de transformer un UserDTO
 * en User
 * 
 */
public interface IEntityFactory {

  IUser createUser(String username, byte[] password, String lastName, String firstName,
      String email, LocalDateTime registerDate, String role, int id);

  IUser createUser(String username, byte[] password, String lastName, String firstName,
      String email, LocalDateTime registerDate, String role);

 
}
