package paoo.cappuccino.util.hasher;

/**
 * Interface used to implement new hash algorithms for a StringHasher.
 *
 * @author Guylian Cox
 */
public interface IHashAlgorithm {

  /**
   * Hashes a string using a given salt.
   *
   * @return The hash encoded as a string.
   */
  IHashHolder hash(final char[] toHash, final IHashHolderDto oldHashData);

  /**
   * Unserializes the algorithm-specific data and generates a new hash holder contaning that data.
   *
   * @param data the data to deserialize.
   * @return a proper hash data holder.
   */
  IHashHolder unserializeCustomData(String data);

  /**
   * Determines if the hash was created using an outdated configuration of this algorithm.
   *
   * @return true: the hash algorithm used is up to date.
   */
  boolean isHashOutdated(IHashHolderDto hash);
}