package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.util.BaseModel;
import paoo.cappuccino.ihm.util.MenuState;
import paoo.cappuccino.ucc.IUserUcc;

public class MenuModel extends BaseModel {

  private final IUserUcc userUcc;
  private final IUserDto connectedUser;
  private MenuState state;

  @Inject
  public MenuModel(IUserUcc userUcc, IUserDto user) {
    this.userUcc = userUcc;
    this.connectedUser = user;
  }

  /**
   * get the username of the connected user
   * 
   * @return username
   */
  public String getPseudoUser() {
    return connectedUser.getUsername();
  }


}
