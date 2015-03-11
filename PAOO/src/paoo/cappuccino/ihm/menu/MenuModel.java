package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.MenuState;
import paoo.cappuccino.ucc.IUserUcc;

public class MenuModel extends BaseModel {

  private final IUserUcc userUcc;
  private MenuState state;

  @Inject
  public MenuModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }



}
