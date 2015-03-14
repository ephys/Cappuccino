package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.MenuState;
import paoo.cappuccino.ucc.IUserUcc;

public class MenuModel extends BaseModel {

  private final IUserUcc userUcc;
  private MenuState state = MenuState.ACCUEIL;

  @Inject
  public MenuModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }

  /**
   * Controls the main view to display. Don't switch view if same view demanded
   * 
   * @param state new state
   */
  public void setState(MenuState state) {
    if (state != this.state) {
      this.state = state;
      switch (state) {
        case ACCUEIL:
          // TODO
          break;
        case CREER_ENTREPRISE:
          // TODO
          break;
        case CREER_JOURNEE:
          // TODO
          break;
        case CREER_PERS_CONTACT:
          // TODO
          break;
        case RECH_ENTREPRISE:
          // TODO
          break;
        case RECH_PARTICIPATION:
          // TODO
          break;
        case RECH_PERS_CONTACT:
          // TODO
          break;
        case SELEC_ENTREPRISES:
          // TODO
          break;
      }
    }
  }

  /**
   * get current state
   * 
   * @return current state
   */
  public MenuState getState() {
    return this.state;
  }



}
