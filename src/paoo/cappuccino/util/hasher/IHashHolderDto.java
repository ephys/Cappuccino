package paoo.cappuccino.util.hasher;

/**
 * Interface used to transfer data used by the hash.
 *
 * @author Guylian Cox
 */
public interface IHashHolderDto {

  /**
   * Gets the actual hash.
   */
  byte[] getHash();

  /**
   * Gets the set of bytes used to salt the hash.
   */
  byte[] getSalt();

  /**
   * Gets the id of the algorithm used to hash the string.
   */
  String getAlgorithmVersion();
}
