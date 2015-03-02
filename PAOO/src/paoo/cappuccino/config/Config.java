package paoo.cappuccino.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import paoo.cappuccino.config.Environment.PROFILE;
import paoo.cappuccino.util.exception.FatalException;

/**
 * @author Laurent Batslé
 */
public class Config {

	private static Properties properties= new Properties();
	private static FileInputStream input=null;
	
	static{
		PROFILE p=Environment.getProfile();
		try {
			switch (p) {
			case TEST:
				input= new FileInputStream("lib/test.properties");
				break;
			case DEV:
				input = new FileInputStream("lib/dev.properties");
				break;
			case PROD:
				input = new FileInputStream("lib/prod.properties");
				break;
			default:
				new FatalException("Environment error!");
				break;
			}
			properties.load(input);
		} catch (FileNotFoundException e) {
			throw new FatalException("Fichier properties manquant!");
		} catch (IOException e) {
			throw new FatalException("Erreur dans les I/O");
		}finally{
			if (input != null){
				try {
					input.close();
				} catch (IOException ignore) {
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
	  if(returnValue==null)
		  throw new FatalException("Classe d'implémentation non trouvée!");
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
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
