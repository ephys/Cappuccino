package paoo.cappuccino.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Handles a properties-based configuration file.
 *
 * @author Laurent Batslé
 */
public final class PropertiesConfig implements IAppConfig {

  private final File configFile;
  private final Properties properties = new Properties();

  /**
   * Creates a application config handler using java properties files.
   *
   * @param configFile The properties config file.
   * @throws IOException the file could not be read.
   */
  public PropertiesConfig(File configFile) throws IOException {
    this.configFile = configFile;

    loadProperties();
  }

  /**
   * Loads the config file from the filesystem
   *
   * @throws paoo.cappuccino.util.exception.FatalException Could not load the config file
   */
  private void loadProperties() throws IOException {
    try (FileInputStream input = new FileInputStream(configFile)) {
      properties.load(input);
    }
  }

  /**
   * Gets a string property from the system configuration
   *
   * @param key the property identifier
   * @return the value of the property
   * @throws java.lang.IllegalArgumentException The provided key was not found in the configuration
   */
  public String getString(String key) {
    String returnValue = properties.getProperty(key);
    if (returnValue == null) {
      throw new IllegalArgumentException("Could not find " + key + " in "
                                         + configFile.getAbsolutePath());
    }

    return returnValue;
  }

  /**
   * Gets an integer property from the system configuration
   *
   * @param key the property identifier
   * @return the value of the property
   * @throws java.lang.IllegalArgumentException The provided key was not found in the configuration
   */
  public int getInt(String key) {
    String val = getString(key);

    try {
      return Integer.parseInt(val);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "There is no integer matching the key " + key + " in " + configFile.getAbsolutePath(), e);
    }
  }
}
