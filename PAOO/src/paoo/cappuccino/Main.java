package paoo.cappuccino;

import paoo.cappuccino.config.Environment;
import paoo.cappuccino.config.injector.DependencyInjector;
import paoo.cappuccino.ihm.ConnexionVue;

public class Main {
  public static void main(String[] args) {
    Environment.getInstance().setup("Cappuccino", "0.0.1");

    ConnexionVue vue = (ConnexionVue) DependencyInjector.buildDependency(ConnexionVue.class);
  }
}
