package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

public class MenuFrame extends BaseFrame {

  /**
   * Creates a new frame for the login gui.
   */
  @Inject
  public MenuFrame(IUserUcc userUcc, IGuiManager guiManager, IUserDto user) {
    // TODO autre dépendences
    super("Cappuccino", 1100, 700);
    this.setResizable(false);

    this.add(new MenuViewController(new MenuModel(userUcc, user),
        guiManager));
    this.setVisible(true);
  }
}
