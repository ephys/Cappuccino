package paoo.cappuccino.util.hasher;

/**
 * Base hash holder class containing methods shared by every other hash holder
 *
 * @author Guylian Cox
 */
public abstract class BaseHashHolder implements IHashHolder {
  private int algorithmVersion;
  private byte[] salt;
  private byte[] hash;

  public BaseHashHolder() {}

  /**
   * Clones an already existing hash data holder.
   *
   * @param hashData the holder to clone.
   */
  public BaseHashHolder(IHashHolderDto hashData) {
    this.setSalt(hashData.getSalt());
    this.setHash(hashData.getHash());
  }

  @Override
  public byte[] getHash() {
    return hash;
  }

  @Override
  public void setHash(byte[] hash) {
    this.hash = hash.clone();
  }

  @Override
  public byte[] getSalt() {
    return salt;
  }

  @Override
  public void setSalt(byte[] salt) {
    this.salt = salt.clone();
  }

  @Override
  public int getAlgorithmVersion() {
    return algorithmVersion;
  }

  @Override
  public void setAlgorithmVersion(int version) {
    this.algorithmVersion = version;
  }
}
