package paoo.cappuccino;

import paoo.cappuccino.config.AppContext;
import paoo.cappuccino.config.Config;
import paoo.cappuccino.config.injector.DependencyInjector;
import paoo.cappuccino.ihm.ConnectionView;
import paoo.cappuccino.util.hasher.StringHasher;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

public class Main {

  public static void main(String[] args) {
    AppContext.INSTANCE.setup("Cappuccino", "0.0.1");

    StringHasher.INSTANCE.addHashAlgorithm(new Pbkdf2Hasher(Config.getInt("pbkdf2_iterations")));

    ConnectionView connectionView = new ConnectionView();
    DependencyInjector.populate(connectionView);
  }
}
