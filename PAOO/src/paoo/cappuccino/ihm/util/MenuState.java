package paoo.cappuccino.ihm.util;

/**
 * State of the main gui menu.
 *
 * @author Opsomer Mathias
 */
public enum MenuState {
  ACCUEIL(1, "Accueil"), RECH_PARTICIPATION(2, "Recherche - Participation"), RECH_PERS_CONTACT(
      3, "Recherche - Personne de contact"), RECH_ENTREPRISE(4,
      "Recherche - Entreprise"), SELEC_ENTREPRISES(5,
      "Sélèctionner des entreprises"), CREER_JOURNEE(6,
      "Créer une journée des entreprises"), CREER_ENTREPRISE(7,
      "créer une entreprise"), CREER_PERS_CONTACT(7,
      "Créer une personne de contact");

  private Integer state;
  private String title;

  /**
   * Enum constructor.
   *
   * @param state the state id.
   * @param title the state title.
   */
  private MenuState(Integer state, String title) {
    this.state = state;
    this.title = title;
  }


  /**
   * Gets the title linked to the state.
   *
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * gets state id.
   *
   * @return state
   */
  public int getState() {
    return this.state;
  }
}
