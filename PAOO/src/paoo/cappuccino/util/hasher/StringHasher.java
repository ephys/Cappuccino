package paoo.cappuccino.util.hasher;

import java.util.ArrayList;

import paoo.cappuccino.util.StringUtils;

/**
 * Simple implementation of the {@link paoo.cappuccino.util.hasher.IStringHasher}
 */
public class StringHasher implements IStringHasher { // a.k.a. The Mighty Abstract Hash Layer

  private ArrayList<IHashAlgorithm> hashAlgorithms = new ArrayList<>(1);

  @Override
  public boolean addHashAlgorithm(IHashAlgorithm algorithm) {
    return hashAlgorithms.add(algorithm);
  }

  @Override
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

  @Override
  public boolean isHashOutdated(final IHashHolderDto hash) {
    return hash.getAlgorithmVersion() != hashAlgorithms.size() - 1
           && hashAlgorithms.get(hash.getAlgorithmVersion()).isHashOutdated(hash);
  }

  @Override
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

  @Override
  public String serialize(IHashHolderDto hash) {
    return String.valueOf(hash.getAlgorithmVersion()) + ':'
           + StringUtils.bytesToString(hash.getHash()) + ':'
           + StringUtils.bytesToString(hash.getSalt()) + ':'
           + ((IHashHolder) hash).serializeCustomData();
  }

  @Override
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
}
