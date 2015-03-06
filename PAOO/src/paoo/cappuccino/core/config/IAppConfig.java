package paoo.cappuccino.core.config;

public interface IAppConfig {
  /**
   * Gets a string property from the system configuration
   *
   * @param key the property identifier
   * @return the value of the property
   * @throws java.lang.IllegalArgumentException The provided key was not found in the configuration
   */
  public String getString(String key);

  /**
   * Gets an integer property from the system configuration
   *
   * @param key the property identifier
   * @return the value of the property
   * @throws java.lang.IllegalArgumentException The provided key was not found in the configuration
   */
  public int getInt(String key);
}
