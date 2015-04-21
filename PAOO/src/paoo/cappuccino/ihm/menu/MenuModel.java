package paoo.cappuccino.ihm.menu;

import java.util.logging.Logger;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.ihm.util.BaseModel;

/**
 * Model used to store the page currently displayed.
 */
public class MenuModel extends BaseModel {

  private final MenuFrame containingFrame;
  private final Logger guiLogger;
  private MenuEntry currentPage = MenuEntry.HOME;
  private Object[] transitionObjects;

  public MenuModel(MenuFrame containingFrame, Logger guiLogger) {
    this.containingFrame = containingFrame;
    this.guiLogger = guiLogger;
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
  public boolean setCurrentPage(MenuEntry page, Object... modelData) {
    if (page == currentPage) {
      return false;
    }

    if (page == null) {
      guiLogger.warning("Page closed");
    } else {
      guiLogger.info("Opening page " + page.getTitle());
    }

    currentPage = page;
    transitionObjects = modelData;
    dispatchChangeEvent();

    return true;
  }

  public Object[] getTransitionObjects() {
    return transitionObjects;
  }

  /**
   * Returns the currently open page.
   */
  public MenuEntry getCurrentPage() {
    return currentPage;
  }
}
