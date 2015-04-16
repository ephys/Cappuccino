package paoo.cappuccino.ihm.core;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;

class CachedResourceManager implements IResourceManager {

  private final IGuiManager guiManager;
  private Map<String, Object> resourceCache = new HashMap<>();

  public CachedResourceManager(IGuiManager cappuccinoIhm) {
    this.guiManager = cappuccinoIhm;
  }

  @Override
  public BufferedImage fetchImage(String path) {
    Object resource = resourceCache.get(path);
    if (resource != null) {
      return (BufferedImage) resource;
    }

    try {
      BufferedImage image = ImageIO.read(new FileInputStream(path));
      resourceCache.put(path, image);

      return image;
    } catch (IOException e) {
      guiManager.getLogger().log(Level.WARNING, "Could not read the requested image " + path, e);

      return null;
    }
  }
}
