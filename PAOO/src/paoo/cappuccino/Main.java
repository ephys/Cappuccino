package paoo.cappuccino;

import paoo.cappuccino.config.Environment;
import paoo.cappuccino.ihm.ConnexionVue;

public class Main {
  public static void main(String[] args) {
    Environment.setup();

    new ConnexionVue();
  }
}
