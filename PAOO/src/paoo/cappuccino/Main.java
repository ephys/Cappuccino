package paoo.cappuccino;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.AppContext.Profile;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.ihm.menu.MenuFrame;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Main for an app with a graphical user interface.
 * 
 * @author Guylian Cox
 */
public class Main extends BaseMain {
  /**
   * Entry point for the Cappuccino application.
   *
   * @param args program args.
   */
  public static void main(String[] args) {
    new Main();
  }
  
  protected Main() {
    super(new AppContext("Cappuccino", "1.0.0"));

    createGui();
  }

  private void createGui() {
    IGuiManager guiManager = getInjector().buildDependency(IGuiManager.class);
    
    if (getContext().getProfileType() != Profile.DEV) {
      guiManager.openFrame(LoginFrame.class);
      return;
    }
    
    guiManager.getLogger().warning("[LoginFrame] Skipping login.");
    guiManager.openFrame(MenuFrame.class).setLoggedUser(new IUserDto() {
      @Override
      public String getUsername() {
        return "john_";
      }

      @Override
      public IHashHolderDto getPassword() {
        return null;
      }

      @Override
      public String getLastName() {
        return "John";
      }

      @Override
      public String getFirstName() {
        return "Smith";
      }

      @Override
      public String getEmail() {
        return "smith@something.net";
      }

      @Override
      public LocalDateTime getRegisterDate() {
        return LocalDateTime.now();
      }

      @Override
      public Role getRole() {
        return Role.USER;
      }

      @Override
      public int getId() {
        return 1;
      }

      @Override
      public int getVersion() {
        return 1;
      }
    });
  }
}
