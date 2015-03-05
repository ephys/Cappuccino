package paoo.cappuccino;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.Config;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.ihm.ConnectionView;
import paoo.cappuccino.util.hasher.StringHasher;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

public class Main {

  public static void main(String[] args) {
    AppContext.INSTANCE.setup("Cappuccino", "1.0.0");
    StringHasher.INSTANCE.addHashAlgorithm(new Pbkdf2Hasher(Config.getInt("pbkdf2_iterations")));

    ConnectionView view =
        (ConnectionView) DependencyInjector.INSTANCE.buildDependency(ConnectionView.class);
  }
}
