package paoo.cappuccino.config;

/**
 * Listener interface for a crash event. That event will be dispatched when the application is about
 * to close because an exception was not caught.
 *
 * Register a crash listener using {@link AppContext#addCrashListener(CrashListener)
 * addCrashListener}.
 *
 * @author Guylian Cox
 */
public interface CrashListener {

  /**
   * Method called when an exception was not caught, causing the application to crash.
   */
  public void onCrash(Throwable e);
}
