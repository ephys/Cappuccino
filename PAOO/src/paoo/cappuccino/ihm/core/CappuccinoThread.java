package paoo.cappuccino.ihm.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Invokes Runnables on a separate thread to avoid freezing swing.
 */
public class CappuccinoThread extends Thread {
  private BlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>();
  private Thread self;
  private boolean isRunning = true;

  /**
   * Creates the new thread.
   */
  public CappuccinoThread() {
    start();
  }

  @Override
  public void run() {
    self = Thread.currentThread();

    while (isRunning) {
      try {
        runnables.take().run();
      } catch (InterruptedException ignore) { }
    }
  }

  /**
   * Runs a runnable. (yup)
   * @param runnable The runnable to run.
   */
  public void invokeLater(Runnable runnable) {
    runnables.add(runnable);
  }

  /**
   * Stops the thread.
   */
  public void die() {
    isRunning = false;
    self.interrupt();
  }
}
