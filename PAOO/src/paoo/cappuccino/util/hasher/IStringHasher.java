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
   * <p>Registers an hash algorithm, the latest registered algorithm will be used to hash new
   * strings while strings already hashed will be checked using the algorithm used to hash them. The
   * order in which the algorithms are registered is important and newer algorithms should always be
   * added after every other.</p>
   *
   * <p>An algorithm should never be removed, instead remove its implementation.</p>
   *
   * @param algorithm An algorithm used to hash strings.
   */
  public boolean addHashAlgorithm(IHashAlgorithm algorithm);

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
  public boolean matchHash(final char[] toHash, final IHashHolderDto currentHashData);

  /**
   * Checks if a hash is using the latest algorithm.
   *
   * @param hash The hash to check.
   * @return true: the hash is using the latest algorithm.
   */
  public boolean isHashOutdated(final IHashHolderDto hash);

  /**
   * Hashes a string using the latest added algorithm.
   *
   * @param toHash The string to hash.
   * @return The hash encoded as a string.
   */
  public IHashHolderDto hash(final char[] toHash);

  /**
   * Translates an hash holder into a string representation of it, for persistence. Use {@link
   * paoo.cappuccino.util.hasher.StringHasher#unserialize(String)} to unserialize the result
   *
   * @param hash The hash holder to serialize.
   * @return The representation of the holder as a string.
   */
  public String serialize(IHashHolderDto hash);

  /**
   * Translates a string representation of a hash holder into an instance of IHashHolderDto
   *
   * @param data the representation of the holder.
   * @return the instance containing the data of the hash.
   */
  public IHashHolderDto unserialize(String data);
}
