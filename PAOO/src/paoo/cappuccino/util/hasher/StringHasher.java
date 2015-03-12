package paoo.cappuccino.util.hasher;

import java.util.Arrays;
import java.util.HashMap;

import paoo.cappuccino.util.StringUtils;

/**
 * Simple implementation of the {@link paoo.cappuccino.util.hasher.IStringHasher}
 *
 * @author Guylian Cox
 */
class StringHasher implements IStringHasher { // a.k.a. The Mighty Abstract Hash Layer

  private IHashAlgorithm preferedAlgorithm;
  private String preferedAlgorithmName;
  private HashMap<String, IHashAlgorithm> hashAlgorithms = new HashMap<>(1);

  @Override
  public boolean addHashAlgorithm(String identifier, IHashAlgorithm algorithm) {
    if (hashAlgorithms.containsKey(identifier)) {
      return false;
    }

    hashAlgorithms.put(identifier, algorithm);
    return true;
  }

  @Override
  public void setPreferedAlgorithm(String identifier) {
    IHashAlgorithm algorithm = hashAlgorithms.get(identifier);

    if (algorithm == null) {
      throw new IllegalArgumentException(
          "There is no algorithm registered under the identifier " + identifier + ".");
    }

    this.preferedAlgorithm = algorithm;
    this.preferedAlgorithmName = identifier;
  }

  @Override
  public boolean matchHash(final char[] toHash, final IHashHolderDto currentHashData) {
    byte[] newHash = hash(toHash, (IHashHolder) currentHashData).getHash();
    byte[] currentHash = currentHashData.getHash();

    return Arrays.equals(newHash, currentHash);
  }

  @Override
  public boolean isHashOutdated(final IHashHolderDto hash) {
    ensureAlgorithmExists();

    return !preferedAlgorithmName.equals(hash.getAlgorithmVersion())
           && preferedAlgorithm.isHashOutdated(hash);

  }

  @Override
  public IHashHolderDto hash(final char[] toHash) {
    ensureAlgorithmExists();

    IHashHolder hashHolder = preferedAlgorithm.hash(toHash, null);
    hashHolder.setAlgorithmVersion(preferedAlgorithmName);
    return hashHolder;
  }

  private IHashHolderDto hash(final char[] toHash, final IHashHolder hashData) {
    final IHashAlgorithm hasher = hashAlgorithms.get(hashData.getAlgorithmVersion());

    if (hasher == null) {
      throw new UnsupportedOperationException(
          "The hash algorithm used to create this hash ("
          + hashData.getAlgorithmVersion()
          + ") is no longer supported.");
    }

    IHashHolder hashHolder = preferedAlgorithm.hash(toHash, hashData);
    hashHolder.setAlgorithmVersion(preferedAlgorithmName);
    return hashHolder;
  }

  @Override
  public String serialize(IHashHolderDto hash) {
    return hash.getAlgorithmVersion() + ':'
           + StringUtils.bytesToString(hash.getHash()) + ':'
           + StringUtils.bytesToString(hash.getSalt()) + ':'
           + ((IHashHolder) hash).serializeCustomData();
  }

  @Override
  public IHashHolderDto unserialize(String data) {
    // version:hash:salt:[algorithm data]
    int versionIndex = data.indexOf(':');
    String version = data.substring(0, versionIndex);

    int hashIndex = data.indexOf(':', versionIndex + 1);
    byte[] hash = StringUtils.strToBytes(data.substring(versionIndex + 1, hashIndex));

    int saltIndex = data.indexOf(':', hashIndex + 1);
    byte[] salt = StringUtils.strToBytes(data.substring(hashIndex + 1, saltIndex));

    IHashAlgorithm hasher = hashAlgorithms.get(version);
    if (hasher == null) {
      throw new UnsupportedOperationException(
          "The hash algorithm used to create this hash ("
          + version
          + ") is no longer supported.");
    }
    IHashHolder unserializedData = hasher.unserializeCustomData(
        data.substring(saltIndex + 1, data.length()));

    unserializedData.setHash(hash);
    unserializedData.setSalt(salt);
    unserializedData.setAlgorithmVersion(version);

    return unserializedData;
  }

  private void ensureAlgorithmExists() {
    if (preferedAlgorithm == null) {
      throw new IllegalStateException("This StringHasher does not have any algorithm implemented. "
                                      + "Use addHashAlgorithm() to add one.");
    }
  }
}
