package paoo.cappuccino.config;

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
 * @author Guylian Cox
 */
public class AppContext {

  public static enum Profile {
    PROD, TEST, DEV
  }

  public static final AppContext INSTANCE = new AppContext();
  private List<CrashListener> crashListeners = new ArrayList<>();
  private Logger appLogger;

  private Profile profileType;
  private String profile;
  private String appName;
  private String version;

  private AppContext() {
    addCrashListener(new CrashWriter());
  };

  public void setup(String appName, String version, String profile) {
    this.profile = profile == null ? "prod" : profile;
    this.appName = appName;
    this.version = version;

    initLogger();
    fetchProfile();
    initGlobalCatcher();

    appLogger.info(appName + " " + version + " launched using profile \"" + this.profile + "\"");
  }

  public void setup(String appName, String version) {
    setup(appName, version, System.getProperty("profile"));
  }

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
  
  private void initGlobalCatcher() {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
      for (CrashListener listener : crashListeners) {
        listener.onCrash(e);
      }
    });
  }

  private void initLogger() {
    appLogger = Logger.getLogger(appName);

    try {
      Formatter formatter = new Formatter() {
        @Override
        public String format(LogRecord record) {
          StringBuilder builder = new StringBuilder();

          builder
              .append('[')
              .append(
                  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
              .append(']');
          builder.append('[').append(record.getLoggerName()).append(']');
          builder.append('[').append(record.getLevel().getLocalizedName()).append(']');
          builder.append(' ').append(record.getMessage());

          return builder.toString();
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
              System.err.write(message.getBytes());
            } else {
              System.out.write(message.getBytes());
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
  
  public Logger getAppLogger() {
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

  public String getProfile() {
    return profile;
  }

  public String getVersion() {
    return version;
  }

  public String getAppName() {
    return appName;
  }
  
  public boolean addCrashListener(CrashListener l) {
    return this.crashListeners.add(l);
  }
  
  public boolean removeCrashListener(CrashListener l) {
    return this.crashListeners.remove(l);
  }
}
