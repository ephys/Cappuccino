package paoo.cappuccino.util.hasher;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import paoo.cappuccino.util.StringUtils;
import paoo.cappuccino.util.exception.FatalException;

/**
 * This utility class handles string hashing. It enables the developer to write multiple hashing
 * algorithms, in case the older ones are not secure enough, without changing anything else in the
 * application and being backwards-compatible with previous algorithms
 */
public class StringHasher {

  public static final int SALT_SIZE = 16;
  public static final StringHasher INSTANCE = new StringHasher();

  private ArrayList<IHashAlgorithm> hashAlgorithms = new ArrayList<>(1);
  private SecureRandom saltGenerator;

  private StringHasher() {
    try {
      saltGenerator = SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      throw new FatalException("Could not fetch the salt generation algorithm", e);
    }
  }

  /**
   * Registers an hash algorithm, the latest registered algorithm will be used to hash new strings
   * while strings already hashed will be checked using the algorithm used to hash them. The order
   * in which the algorithms are registered is important and newer algorithms should always be added
   * after every other.
   *
   * An algorithm should never be removed, instead remove its implementation.
   *
   * @param algorithm An algorithm used to hash strings.
   */
  public boolean addHashAlgorithm(IHashAlgorithm algorithm) {
    return hashAlgorithms.add(algorithm);
  }

  /**
   * Checks a given set of byte is the hash of a given string.
   *
   * @param toHash          The string to compare to the hash.
   * @param currentHashData The hash to match. Its data follows the pattern "hash:algorithm_id:salt:algorithm_data".
   *                        Obtained via {@link paoo.cappuccino.util.hasher.StringHasher#hash(String)
   *                        hash()}
   */
  public boolean matchHash(final String toHash, final IHashHolderDto currentHashData) {
    byte[] newHash = hash(toHash, (IHashHolder) currentHashData).getHash();
    byte[] currentHash = currentHashData.getHash();

    if (currentHash.length != newHash.length) {
      return false;
    }

    for (int i = 0; i < newHash.length; i++) {
      if (newHash[i] != currentHash[i]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Hashes a string using the latest added algorithm.
   *
   * @param toHash The string to hash.
   * @return The hash encoded as a string.
   */
  public IHashHolderDto hash(final String toHash) {
    if (hashAlgorithms.size() == 0) {
      throw new IllegalStateException(
          "This StringHasher does not have any algorithm implemented. Use addHashAlgorithm() to add one.");
    }

    final IHashAlgorithm hasher = hashAlgorithms.get(hashAlgorithms.size() - 1);
    return hasher.hash(toHash, null);
  }

  private IHashHolderDto hash(final String toHash, final IHashHolder hashData) {
    final IHashAlgorithm hasher = hashAlgorithms.get(hashData.getAlgorithmVersion());

    return hasher.hash(toHash, hashData);
  }

  /**
   * Translates an hash holder into a string representation of it, for persistence. Use {@link
   * paoo.cappuccino.util.hasher.StringHasher#unserialize(String)} to unserialize the result
   *
   * @param hash The hash holder to serialize.
   * @return The representation of the holder as a string.
   */
  public String serialize(IHashHolderDto hash) {
    return String.valueOf(hash.getAlgorithmVersion()) + ':'
           + StringUtils.bytesToString(hash.getHash()) + ':'
           + StringUtils.bytesToString(hash.getSalt()) + ':'
           + ((IHashHolder) hash).serializeCustomData();
  }

  /**
   * Translates a string representation of a hash holder into an instance of IHashHolderDto
   *
   * @param data the representation of the holder.
   * @return the instance containing the data of the hash.
   */
  public IHashHolderDto unserialize(String data) {
    // version:hash:salt:[algorithm data]
    int versionIndex = data.indexOf(':');
    int version = Integer.parseInt(data.substring(0, versionIndex));

    int hashIndex = data.indexOf(':', versionIndex + 1);
    byte[] hash = StringUtils.strToBytes(data.substring(versionIndex + 1, hashIndex));

    int saltIndex = data.indexOf(':', hashIndex + 1);
    byte[] salt = StringUtils.strToBytes(data.substring(hashIndex + 1, saltIndex));

    IHashAlgorithm hasher = hashAlgorithms.get(version);
    IHashHolder
        unserializedData = hasher.unserializeCustomData(
        data.substring(saltIndex + 1, data.length()));

    unserializedData.setHash(hash);
    unserializedData.setSalt(salt);
    unserializedData.setAlgorithmVersion(version);

    return unserializedData;
  }

  /**
   * Generates a byte array containing random numbers.
   */
  public byte[] genSalt() {
    byte[] salt = new byte[SALT_SIZE];
    saltGenerator.nextBytes(salt);

    return salt;
  }
}
