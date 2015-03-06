package paoo.cappuccino.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Handles a properties-based configuration file
 *
 * @author Laurent Batslé
 */
public final class PropertiesConfig implements IAppConfig {

  private final File configFile;
  private final Properties properties = new Properties();
  private final boolean bDebug;

  /**
   * Creates a application config handler using java properties files.
   *
   * @param configFile The properties config file.
   * @param debug whether or not the handler should debug when a problem occurs
   *              (like add the missing entry to the config file).
   * @throws IOException the file could not be read.
   */
  public PropertiesConfig(File configFile, boolean debug) throws IOException {
    this.configFile = configFile;
    this.bDebug = debug;

    loadProperties();
  }

  /**
   * Loads the config file from the filesystem
   *
   * @throws paoo.cappuccino.util.exception.FatalException Could not load the config file
   */
  private void loadProperties() throws IOException {
    FileInputStream input = new FileInputStream(configFile);
    properties.load(input);

    try {
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
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
      if (bDebug) {
        properties.setProperty(key, "TODO: set me.");

        try {
          properties.store(new FileOutputStream(configFile), "hello !");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

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
