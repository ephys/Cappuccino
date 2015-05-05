package paoo.cappuccino;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.config.IAppConfig;
import paoo.cappuccino.core.config.PropertiesConfig;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.hasher.IStringHasher;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

/**
 * Base main, setups what is essentially required in order to have a working application.
 *
 * @author Guylian Cox
 */
public class BaseMain {
  private static final File RESOURCES_FOLDER = new File("lib");

  private final AppContext appContext;
  private final DependencyInjector injector;

  /**
   * Instantiates an application with the configuration stored in the given AppContext
   * @param context The app configuration.
   */
  public BaseMain(AppContext context) {
    this.appContext = context;
    this.injector = createDependencies(appContext);

    context.getAppLogger().info(context.getAppName() + " " + context.getVersion()
        + " launched using profile \"" + context.getProfile()
        + "\" (" + context.getProfileType().name() + ")");
  }

  /**
   * Gets the application dependency injector.
   */
  public DependencyInjector getInjector() {
    return injector;
  }


  /**
   * Gets the application context.
   */
  public AppContext getContext() {
    return appContext;
  }

  /**
   * Configures the application. Separated so that it can be used to setup the test environment too.
   *
   * @param appContext the application context.
   * @return The application dependency injector.
   * @throws paoo.cappuccino.util.exception.FatalException An exception made it impossible to
   *         configure the application.
   */
  private DependencyInjector createDependencies(AppContext appContext) {
    IAppConfig appConfig =
        makeConfig(appContext.getProfile() + ".properties");

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
   * @return The newly created config handler.
   * @throws paoo.cappuccino.util.exception.FatalException The config file could not be loaded
   */
  private IAppConfig makeConfig(final String filename) {
    if (!RESOURCES_FOLDER.exists() && !RESOURCES_FOLDER.mkdirs()) {
      throw new FatalException("Could not make config directory "
          + RESOURCES_FOLDER.getAbsolutePath());
    }

    final File configFile = new File(RESOURCES_FOLDER, filename);

    try {
      return new PropertiesConfig(configFile);
    } catch (IOException e) {
      throw new FatalException("Could not load the config file " + configFile.getAbsolutePath(), e);
    }
  }
}
