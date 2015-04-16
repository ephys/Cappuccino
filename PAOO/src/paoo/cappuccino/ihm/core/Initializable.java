package paoo.cappuccino.ihm.core;

/**
 * Marks models as being Initializable, when a page change is called, the new model can receive data
 * from another page.
 */
public interface Initializable {

  /**
   * Inits the object.
   * @param data The data used to initialize the object.
   */
  void init(Object[] data);
}
