package paoo.cappuccino.core.config;

/**
 * Handles a configuration file.
 *
 * @author Guylian Cox
 */
public interface IAppConfig {

  /**
   * Gets a string property from the system configuration.
   *
   * @param key the property identifier.
   * @return the value of the property.
   * @throws java.lang.IllegalArgumentException The provided key was not found in the
   *                                            configuration.
   */
  String getString(String key);

  /**
   * Gets an integer property from the system configuration.
   *
   * @param key the property identifier.
   * @return the value of the property.
   * @throws java.lang.IllegalArgumentException The provided key was not found in the
   *                                            configuration.
   */
  int getInt(String key);
}
