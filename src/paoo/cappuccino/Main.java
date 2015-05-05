package paoo.cappuccino;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;

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
    super(new AppContext("Cappuccino", "1.2.0"));

    createGui();
  }

  private void createGui() {
    IGuiManager guiManager = getInjector().buildDependency(IGuiManager.class);

    guiManager.openFrame(LoginFrame.class);
  }
}
