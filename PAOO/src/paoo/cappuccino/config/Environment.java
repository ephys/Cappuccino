package paoo.cappuccino.config;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import paoo.cappuccino.util.exception.FatalException;

public class Environment {

  public static enum PROFILE {
    PROD, TEST, DEV
  }

  private static final File REPORTS_FOLDER = new File("crash-reports");

  private static PROFILE profile = PROFILE.PROD;
  private static final Logger appLogger = Logger.getLogger("Cappuccino-global");

  public static void setup() {
    initLogger();

    String profileFlag = System.getProperty("profile");
    if (profileFlag != null) {
      switch (profileFlag) {
        case "prod":
          profile = PROFILE.PROD;
          break;
        case "test":
          profile = PROFILE.TEST;
          break;
        case "dev":
          profile = PROFILE.DEV;
          break;
        default:
          throw new FatalException("Could not set the application environment, " + profileFlag
              + " isn't a valid profile.");
      }
    }

    appLogger.info("Cappuccino 0.0.1 launched using profile " + profileFlag);

    initGlobalCatcher();
  }

  private static void initLogger() {
    try {
      Formatter formatter = new SimpleFormatter();

      appLogger.addHandler(new FileHandler("app.log"));
      appLogger.setParent(Logger.getGlobal());

      Handler[] handlers = appLogger.getHandlers();
      for (Handler h : handlers) {
        h.setLevel(Level.ALL);
        h.setFormatter(formatter);
      }
    } catch (IOException e) {
      throw new FatalException("Could not setup the application logger", e);
    }
  }

  private static void initGlobalCatcher() {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
      // TODO: warn the IHM, via event handler ?

        String reportFile = writeCrashReport(e);
        appLogger.severe("A fatal exception has occurred");
        if (reportFile != null) {
          appLogger.severe("the details have been saved to " + reportFile);
        } else {
          appLogger.severe("the details could not be saved, the crash-reports folder "
              + REPORTS_FOLDER.getAbsolutePath() + " cannot be created");
        }
      });
  }


  private static String writeCrashReport(Throwable exception) {
    exception.printStackTrace();

    if (!REPORTS_FOLDER.exists() && !REPORTS_FOLDER.mkdirs()) {
      return null;
    }

    LocalDateTime crashtime = LocalDateTime.now();
    File file = new File(REPORTS_FOLDER, crashtime.toString() + ".log");

    try {
      file.createNewFile();
      PrintWriter writer = new PrintWriter(file);

      writer.write("/// Cappuccino ///\n");
      writer.write("Date: " + crashtime.format(DateTimeFormatter.BASIC_ISO_DATE) + "\n\n");

      writer.write("Exception message: " + exception.getMessage() + "\n");

      writer.write("Exception stacktrace:\n");
      exception.printStackTrace(writer);

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return file.getAbsolutePath();

  }

  /**
   * Creates a logger for a part of the application.
   *
   * @param appLayer The part of the application requesting a logger (IHM, DAL, DALSQL, UCC, ...)
   * @return a logger.
   */
  public static Logger getLogger(String appLayer) {
    Logger layerLogger = Logger.getLogger("Cappuccino-" + appLayer);
    layerLogger.setParent(appLogger);

    return layerLogger;
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
  public static PROFILE getProfile() {
    return profile;
  }
}
