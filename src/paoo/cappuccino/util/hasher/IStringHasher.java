package paoo.cappuccino.util.hasher;

/**
 * This handles string hashing. It enables the developer to write multiple hashing algorithms, in
 * case the older ones are not secure enough, without changing anything else in the application and
 * being backwards-compatible with hashes created using previous algorithms
 *
 * @author Guylian Cox
 */
public interface IStringHasher {

  /**
   * <p>Registers an hash algorithm, the algorithm marked as preferred registered algorithm will be
   * used to hash new strings while strings already hashed will be checked using the algorithm used
   * to hash them.</p>
   *
   * <p>You're free to remove an algorithm once it isn't used by any legacy hash anymore.</p>
   *
   * @param identifier A key used to identify the algorithm used to hash a string.
   * @param algorithm  An algorithm used to hash strings.
   */
  boolean addHashAlgorithm(String identifier, IHashAlgorithm algorithm);

  /**
   * Makes the string hasher use that algorithm for newer hashes.
   *
   * @param identifier The identifier of a previously registered hash algorithm.
   * @throws java.lang.IllegalArgumentException No algorithm is registered under that identifier.
   */
  void setPreferedAlgorithm(String identifier);

  /**
   * Checks a given set of byte is the hash of a given string.
   *
   * @param toHash          The string to compare to the hash.
   * @param currentHashData The hash to match. Its data follows the pattern
   *                        "hash:algorithm_id:salt:algorithm_data".
   *                        Obtained via
   *                        {@link paoo.cappuccino.util.hasher.StringHasher#hash(char[])}
   * @return true: the given hash is the given string's hash
   */
  boolean matchHash(final char[] toHash, final IHashHolderDto currentHashData);

  /**
   * Checks if a hash is using the latest algorithm.
   *
   * @param hash The hash to check.
   * @return true: the hash is using the latest algorithm.
   */
  boolean isHashOutdated(final IHashHolderDto hash);

  /**
   * Hashes a string using the latest added algorithm.
   *
   * @param toHash The string to hash.
   * @return The hash encoded as a string.
   */
  IHashHolderDto hash(final char[] toHash);

  /**
   * Translates an hash holder into a string representation of it, for persistence. Use {@link
   * paoo.cappuccino.util.hasher.StringHasher#deserialize(String)} to deserialize the result
   *
   * @param hash The hash holder to serialize.
   * @return The representation of the holder as a string.
   */
  String serialize(IHashHolderDto hash);

  /**
   * Translates a string representation of a hash holder into an instance of IHashHolderDto
   *
   * @param data the representation of the holder.
   * @return the instance containing the data of the hash.
   */
  IHashHolderDto deserialize(String data);
}
