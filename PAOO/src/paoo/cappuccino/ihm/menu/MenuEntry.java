package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.ihm.accueil.AccueilViewController;

/**
 * List of entries of the main menu.
 *
 * @author Opsomer Mathias
 */
public enum MenuEntry {
  HOME("Accueil", AccueilViewController.class),
  SEARCH_PARTICIPATION("Recherche - Participation", null),
  SEARCH_CONTACT("Recherche - Personne de contact", null),
  SEARCH_COMPANY("Recherche - Entreprise", null),
  SELEC_COMPANY("Sélectionner des entreprises", null),
  CREATE_BDAY("Créer une journée des entreprises", null),
  CREATE_COMPANY("créer une entreprise", null),
  CREATE_CONTACT("Créer une personne de contact", null);

  private final String title;
  private final Class<?> viewController;
  private Object model;

  /**
   * @param title the menu entry's title.
   * @param viewController The ViewController of the page attached to that entry.
   */
  private MenuEntry(String title, Class<?> viewController) {
    this.title = title;
    this.viewController = viewController;
  }

  /**
   * Gets the title linked to the state.
   *
   * @return title
   */
  public String getTitle() {
    return title;
  }

  Class<?> getViewController() {
    return viewController;
  }

  Object getModel() {
    return model;
  }

  void setModel(Object model) {
    this.model = model;
  }
}
