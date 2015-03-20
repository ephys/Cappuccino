package paoo.cappuccino.ihm.menu;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model used to store the page currently displayed.
 */
public class MenuModel extends BaseModel {

  private final MenuFrame containingFrame;
  private MenuEntry currentPage = MenuEntry.HOME;

  public MenuModel(MenuFrame containingFrame) {
    this.containingFrame = containingFrame;
  }

  /**
   * gets the connected user.
   */
  public IUserDto getLoggedUser() {
    return containingFrame.getLoggedUser();
  }

  /**
   * Changes the main view.
   *
   * @param page the page to open.
   */
  public boolean setCurrentPage(MenuEntry page) {
    if (page == currentPage) {
      return false;
    }

    currentPage = page;
    dispatchChangeEvent();

    return true;
  }

  public MenuEntry getCurrentPage() {
    return currentPage;
  }
}
