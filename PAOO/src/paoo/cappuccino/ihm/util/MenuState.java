package paoo.cappuccino.ihm.util;

/**
 * State of the menu ihm
 *
 * @author Opsomer Mathias
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
   * Constructor
   * 
   * @param state the state
   * @param titre the title linked tot this state
   */
  private MenuState(Integer state, String titre) {
    this.state = state;
    this.titre = titre;
  }


  /**
   * Get title linked to a state
   *
   * @return String title
   */
  public String getTitre() {
    return titre;
  }
}
