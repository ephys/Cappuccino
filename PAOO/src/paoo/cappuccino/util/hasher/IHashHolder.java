package paoo.cappuccino.util.hasher;

public interface IHashHolder extends IHashHolderDto {

  /**
   * Sets the hash.
   *
   * @param hash A table of bytes
   */
  void setHash(final byte[] hash);

  /**
   * Sets the hash
   */
  void setSalt(final byte[] salt);

  /**
   * Sets the id of the algorithm used to hash the string
   *
   * @param version the algorithm id
   */
  void setAlgorithmVersion(final int version);

  /**
   * Converts the hash holder's algorithm custom data into a string representation for persistence.
   *
   * @return the data encoded as a string
   */
  String serializeCustomData();
}
