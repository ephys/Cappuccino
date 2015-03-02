package paoo.cappuccino.business.entity;

import paoo.cappuccino.business.dto.IUserDto;


/**
 * 
 * Interface de toutes les fonctions biz-ness d'un utilisateur
 * 
 */
public interface IUser extends IUserDto, IBaseEntity {
  /**
   * Verifie la validite du mot de passe qu'un utilisateur a rentre dans l'IHM avec celui present
   * dans la base de données
   * 
   * @param password mot de passe qu'un utilisateur a rentre dans l'IHM
   * @return true si le mot de passe est correct, false sinon.
   */
  boolean authenticatePassword(byte[] password);
}
