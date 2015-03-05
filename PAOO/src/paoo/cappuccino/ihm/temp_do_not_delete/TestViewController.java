package paoo.cappuccino.ihm.temp_do_not_delete;

import paoo.cappuccino.core.injector.Inject;

public class TestViewController extends TestController {

  @Inject
  public TestViewController(TestModel model) {
    super(model);
    this.add(new TestView(model));
  }
}
