package paoo.cappuccino;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.core.config.PropertiesConfig;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.login.LoginFrame;
import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.hasher.IStringHasher;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

public class Main {

  private static final File RESOURCES_FOLDER = new File("lib");

  /**
   * Entry point for the Cappuccino application.
   *
   * @param args program args.
   */
  public static void main(String[] args) {
    AppContext appContext = new AppContext("Cappuccino", "1.0.0");
    DependencyInjector injector = configureApp(appContext);

    IGuiManager guiManager = injector.buildDependency(IGuiManager.class);
    guiManager.openFrame(LoginFrame.class);
  }

  /**
   * Configures the application. Separated so that it can be used to setup the test environment
   * too.
   *
   * @param appContext the application context.
   * @return The application dependency injector.
   * @throws paoo.cappuccino.util.exception.FatalException An exception made it impossible to
   *                                                       configure the application.
   */
  public static DependencyInjector configureApp(AppContext appContext) {
    IAppConfig appConfig =
        makeConfig(appContext.getProfile() + ".properties",
                   appContext.getProfileType() == AppContext.Profile.DEV);

    DependencyInjector injector = new DependencyInjector(appConfig, appContext);
    injector.setDependency(AppContext.class, appContext);
    injector.setDependency(IAppConfig.class, appConfig);

    IStringHasher hasher = injector.buildDependency(IStringHasher.class);

    try {
      hasher.addHashAlgorithm("pbkdf2", new Pbkdf2Hasher(appConfig.getInt("pbkdf2_iterations")));
      hasher.setPreferedAlgorithm("pbkdf2");
    } catch (NoSuchAlgorithmException e) {
      throw new FatalException("Could not fetch the hashing algorithm", e);
    }

    return injector;
  }

  /**
   * Loads the config file or dies.
   *
   * @param filename The name of the config file.
   * @param debug    Whether or not the config handler should debug
   * @return The newly created config handler.
   * @throws paoo.cappuccino.util.exception.FatalException The config file could not be loaded
   */
  private static IAppConfig makeConfig(final String filename, final boolean debug) {
    if (!RESOURCES_FOLDER.exists() && !RESOURCES_FOLDER.mkdirs()) {
      throw new FatalException("Could not make config directory "
                               + RESOURCES_FOLDER.getAbsolutePath());
    }

    final File configFile = new File(RESOURCES_FOLDER, filename);

    try {
      return new PropertiesConfig(configFile, debug);
    } catch (IOException e) {
      throw new FatalException("Could not load the config file " + configFile.getAbsolutePath(), e);
    }
  }
}
