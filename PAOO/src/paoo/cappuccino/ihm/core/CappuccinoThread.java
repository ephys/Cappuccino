package paoo.cappuccino.ihm.core;

import java.util.LinkedList;

/**
 * Invokes Runnables on a separate thread to avoid freezing swing.
 */
public class CappuccinoThread extends Thread {
  private LinkedList<Runnable> runnables = new LinkedList<>();
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
      if (runnables.size() == 0) {
        try {
          Thread.sleep(60000);
        } catch (InterruptedException ignore) {
        }

        continue;
      }

      runnables.removeFirst().run();
    }
  }

  /**
   * Runs a runnable. (yup)
   * @param runnable The runnable to run.
   */
  public void invokeLater(Runnable runnable) {
    runnables.addLast(runnable);

    self.interrupt();
  }

  /**
   * Stops the thread.
   */
  public void die() {
    isRunning = false;
  }
}
