package paoo.cappuccino.ihm.menu;

/**
 * List of entries of the main menu.
 *
 * @author Opsomer Mathias
 */
public enum MenuEntry {
  HOME("Accueil"),
  SEARCH_PARTICIPATION("Recherche - Participation"),
  SEARCH_CONTACT("Recherche - Personne de contact"),
  SEARCH_COMPANY("Recherche - Entreprise"),
  SELECT_COMPANY("Sélectionner des entreprises"),
  CREATE_BDAY("Créer une journée des entreprises"),
  CREATE_COMPANY("créer une entreprise"),
  CREATE_CONTACT("Créer une personne de contact");

  private final String title;

  /**
   * @param title the menu entry's title.
   */
  private MenuEntry(String title) {
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
}
