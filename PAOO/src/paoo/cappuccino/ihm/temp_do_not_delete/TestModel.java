package paoo.cappuccino.ihm.temp_do_not_delete;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.ihm.temp_do_not_delete.util.BaseModel;
import paoo.cappuccino.ucc.IUserUcc;

public class TestModel extends BaseModel {

  private final IUserUcc userUcc;

  @Inject
  public TestModel(IUserUcc userUcc) {
    this.userUcc = userUcc;
  }
}
