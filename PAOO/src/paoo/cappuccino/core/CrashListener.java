package paoo.cappuccino.core;

/**
 * <p>Listener interface for a crash event. That event will be dispatched when the application is
 * about to close because an exception was not caught.</p>
 *
 * <p>Register a crash listener using {@link AppContext#addCrashListener(CrashListener)}.</p>
 *
 * @author Guylian Cox
 */
public interface CrashListener {

  /**
   * Method called when an exception was not caught, causing the application to crash.
   */
  public void onCrash(Throwable fatalException);
}
