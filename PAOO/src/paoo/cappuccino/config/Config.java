package paoo.cappuccino.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import paoo.cappuccino.util.exception.FatalException;

/**
 * @author Laurent Batslé
 */
public class Config {

  private static final File PROPS_FOLDER = new File("lib");

  private static File configFile;
  private static Properties properties = new Properties();

  static {
    if (!PROPS_FOLDER.exists() && !PROPS_FOLDER.mkdirs()) {
      throw new FatalException("Could not make config directory " + PROPS_FOLDER.getAbsolutePath());
    }
    configFile = new File(PROPS_FOLDER, AppContext.INSTANCE.getProfile() + ".properties");

    FileInputStream input = null;
    try {
      input = new FileInputStream(configFile);
      properties.load(input);
    } catch (IOException e) {
      throw new FatalException("Could not load the configuration file "
                               + configFile.getAbsolutePath(), e);
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Gets a string property from the system configuration
   *
   * @param key the property identifier
   * @return the value of the property
   * @throws java.lang.IllegalArgumentException The provided key was not found in the configuration
   */
  public static String getString(String key) {
    String returnValue = properties.getProperty(key);
    if (returnValue == null) {
      if (AppContext.INSTANCE.getProfileType() == AppContext.Profile.DEV) {
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
  public static int getInt(String key) {
    String val = getString(key);

    try {
      return Integer.valueOf(val);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("There is no integer matching the key " + key + " in " + configFile.getAbsolutePath(), e);
    }
  }
}
