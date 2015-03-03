package paoo.cappuccino.ihm.utils;

/**
 * enumérer pour l'état dans lequel la frame principale ce trouve
 *
 * @author Opsomer Mathias
 *
 *
 */
public enum MenuState {
  ACCUEIL(1, "Accueil"), RECH_PARTICIPATION(2, "Recherche - Participation"), RECH_PERS_CONTACT(3,
      "Recherche - Personne de contact"), RECH_ENTREPRISE(4, "Recherche - Entreprise"), SELEC_ENTREPRISES(
          5, "Sélèctionner des entreprises"), CREER_JOURNEE(6, "Créer une journée des entreprises"), CREER_ENTREPRISE(
              7, "créer une entreprise"), CREER_PERS_CONTACT(7, "Créer une personne de contact");

  private Integer state;
  private String titre;

  /**
   * construit l'état sur base d'un integer
   */
  private MenuState(Integer state, String titre) {
    this.state = state;
    this.titre = titre;
  }

  /**
   * getState l'état courrant
   *
   * @return int state l'état courrant
   */
  public int getState() {
    return state;
  }

  /**
   * getTitre le titre correspondant à l'état courrant
   *
   * @return String le titre
   */
  public String getTitre() {
    return titre;
  }
}
