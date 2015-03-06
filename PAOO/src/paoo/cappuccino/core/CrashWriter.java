package paoo.cappuccino.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Crash listener used to write uncaught exceptions to file.
 *
 * @author Guylian Cox
 */
class CrashWriter implements CrashListener {
  private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
  private static final File REPORTS_FOLDER = new File("crash-reports");

  private final AppContext appContext;

  public CrashWriter(AppContext appContext) {
    this.appContext = appContext;
  }

  /**
   * Logs an exception to a file.
   *
   * @param exception The exception to log.
   * @return The name of the file the exception has been written to.
   */
  private String writeCrashReport(Throwable exception) {
    if (!REPORTS_FOLDER.exists() && !REPORTS_FOLDER.mkdirs()) {
      return null;
    }

    LocalDateTime crashTime = LocalDateTime.now();
    File file =
        new File(REPORTS_FOLDER, crashTime.format(FILENAME_FORMATTER) + ".log");

    try {
      if (!file.createNewFile()) {
        appContext.getAppLogger().severe("Could not create file " + file.getAbsolutePath());
        return null;
      }

      PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8.name());

      writer.write("/// Cappuccino ///\n");
      writer.write("Date: " + crashTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n\n");

      writer.write("Exception message: " + exception.getMessage() + "\n");

      writer.write("Exception stacktrace:\n");
      exception.printStackTrace(writer);

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();

      return null;
    }

    return file.getAbsolutePath();
  }

  @Override
  public void onCrash(Throwable crashSource) {
    String reportFile = writeCrashReport(crashSource);

    Logger logger = appContext.getAppLogger();

    logger.severe("A fatal exception has occurred");
    if (reportFile != null) {
      logger.severe("The details have been saved to " + reportFile);
    } else {
      logger.severe("The details could not be saved, the crash-reports folder "
          + REPORTS_FOLDER.getAbsolutePath() + " cannot be created");
      crashSource.printStackTrace();
    }
  }
}
