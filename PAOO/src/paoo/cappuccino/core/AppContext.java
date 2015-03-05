package paoo.cappuccino.core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import paoo.cappuccino.util.exception.FatalException;

/**
 * Configuration specific to the application environment
 *
 * @author Guylian Cox
 */
public class AppContext {

  public static final AppContext INSTANCE = new AppContext();
  private List<CrashListener> crashListeners = new ArrayList<>();
  private Logger appLogger;
  private Profile profileType = Profile.PROD;
  private String profile = "prod";
  private String appName;
  private String version;

  private AppContext() {
    addCrashListener(new CrashWriter());
  }

  /**
   * Configures the environment
   *
   * @param appName The application name
   * @param version The application version
   * @param profile The profile to load
   */
  public void setup(String appName, String version, String profile) {
    if (profile != null) {
      this.profile = profile;
    }

    this.appName = appName;
    this.version = version;

    initLogger();
    fetchProfile();
    initGlobalCatcher();

    appLogger.info(appName + " " + version + " launched using profile \"" + this.profile + "\"");
  }

  /**
   * Configures the environment. The profile to load is set from the jvm arguments
   *
   * @param appName The application name
   * @param version The application version
   */
  public void setup(String appName, String version) {
    setup(appName, version, System.getProperty("profile"));
  }

  /**
   * Parses the profile name and extrapolates the profile type from it.
   */
  private void fetchProfile() {
    switch (profile) {
      case "prod":
        profileType = Profile.PROD;
        break;
      case "dev":
        profileType = Profile.DEV;
        break;
      case "test":
      default:
        profileType = Profile.TEST;
        break;
    }
  }

  /**
   * Creates the application global catcher
   */
  private void initGlobalCatcher() {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
      for (CrashListener listener : crashListeners) {
        listener.onCrash(e);
      }

      e.printStackTrace();
    });
  }

  /**
   * Setups the application main logger
   */
  private void initLogger() {
    appLogger = Logger.getLogger(appName);

    try {
      Formatter formatter = new Formatter() {
        @Override
        public String format(LogRecord record) {

          return "["
                 + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                 + "][" + record.getLoggerName()
                 + "][" + record.getLevel().getLocalizedName()
                 + "] " + record.getMessage() + "\r\n";
        }
      };

      Handler logFile = new FileHandler("app.log");
      logFile.setFormatter(formatter);
      logFile.setLevel(Level.ALL);

      Handler logConsole = new ConsoleHandler() {
        public void publish(LogRecord record) {
          try {
            String message = getFormatter().format(record);
            if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
              System.err.print(message);
            } else {
              System.out.print(message);
            }
          } catch (Exception e) {
            throw new FatalException("Logger error", e);
          }
        }
      };
      logConsole.setFormatter(formatter);

      appLogger.addHandler(logConsole);
      appLogger.addHandler(logFile);

      appLogger.setParent(Logger.getGlobal());
      appLogger.setUseParentHandlers(false);
    } catch (IOException e) {
      throw new FatalException("Could not setup the application logger", e);
    }
  }

  /**
   * Creates a logger for a part of the application.
   *
   * @param appLayer The part of the application requesting a logger (IHM, DAL, DALSQL, UCC, ...)
   * @return a logger.
   */
  public Logger getLogger(String appLayer) {
    Logger layerLogger = Logger.getLogger("Capp-" + appLayer);
    layerLogger.setParent(appLogger);

    return layerLogger;
  }

  /**
   * Returns the application parent/global logger
   */
  Logger getAppLogger() {
    return appLogger;
  }

  /**
   * Gets the application profile, defaults to PROFILES.PROD if not set. This variable allows the
   * application to know whether the system is in production, in development or in tests.
   *
   * It can be changed using the "-Dprofile" flag when running the application: "prod" for a
   * production environment, "test" for a testing environment and "dev" for a development
   * environment
   *
   * @return the current profile type.
   */
  public Profile getProfileType() {
    return profileType;
  }

  /**
   * Gets the application profile name
   */
  public String getProfile() {
    return profile;
  }

  /**
   * Gets the application version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Gets the application name
   */
  public String getAppName() {
    return appName;
  }

  /**
   * Adds a listener that will be called just before the application crashes
   *
   * @param listener the listener to call
   * @return true: the listener has been added
   */
  public boolean addCrashListener(CrashListener listener) {
    return this.crashListeners.add(listener);
  }

  /**
   * Removes a crash listener
   *
   * @param listener the listener to remove
   * @return true: the listener has been removed
   */
  public boolean removeCrashListener(CrashListener listener) {
    return this.crashListeners.remove(listener);
  }

  public static enum Profile {
    PROD, TEST, DEV
  }
}
