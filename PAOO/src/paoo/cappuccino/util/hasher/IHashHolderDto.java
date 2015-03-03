package paoo.cappuccino.util.hasher;

import java.io.Serializable;

/**
 * Hash data transfer object
 *
 * @author Guylian Cox
 */
public interface IHashHolderDto extends Serializable {

  /**
   * Gets the actual hash.
   */
  byte[] getHash();

  /**
   * Gets the set of bytes used to salt the hash.
   */
  byte[] getSalt();

  /**
   * The id of the algorithm used to hash the string
   */
  int getAlgorithmVersion();
}
