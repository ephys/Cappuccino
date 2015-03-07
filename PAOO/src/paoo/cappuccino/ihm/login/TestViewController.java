package paoo.cappuccino.ihm.login;

import paoo.cappuccino.core.injector.Inject;

public class TestViewController extends TestController {

  @Inject
  public TestViewController(LoginModel model) {
    super(model);
    this.add(new TestView(model));
  }
}
