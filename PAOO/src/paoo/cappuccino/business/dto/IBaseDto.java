package paoo.cappuccino.business.dto;

public interface IBaseDto {

  /**
   * Recupere l'identifiant de l'entite
   */
  int getId();

  /**
   * recupere le numero de version dans la base de donnée
   */
  int getVersion();
}
