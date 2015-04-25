package paoo.cappuccino;

import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.IHashHolderDto;
import paoo.cappuccino.util.hasher.IStringHasher;

public class Hasher extends BaseMain {
  
  @Inject
  private IStringHasher hasher;
  
  public Hasher(String[] toHash) {
    super(new AppContext("String Hasher", "0.0.1", "prod"));
    
    getInjector().populate(this);
    
    for (String param : toHash) {
      IHashHolderDto hash = hasher.hash(param.toCharArray());
      getContext().getAppLogger().info(param + " -> " + hasher.serialize(hash));
    }
  }

  public static void main(String[] params) {
    Hasher app = new Hasher(params);
  }
}
