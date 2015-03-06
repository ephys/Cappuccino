package paoo.cappuccino.util.hasher.pbkdf2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.hasher.IHashAlgorithm;
import paoo.cappuccino.util.hasher.IHashHolder;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Hash algorithm based on PBKDF2
 *
 * @author Guylian Cox
 */
public final class Pbkdf2Hasher implements IHashAlgorithm {

  public static final int SALT_SIZE = 16;
  public static final int HASH_SIZE = 8 * 64;
  private final int nbIterations;

  private SecureRandom saltGenerator;
  private SecretKeyFactory hashAlgorithm;

  /**
   * Constructor~
   *
   * @param nbIterations the number of iterations PBKDF2 should do.
   */
  public Pbkdf2Hasher(int nbIterations) throws NoSuchAlgorithmException {
    hashAlgorithm = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    saltGenerator = SecureRandom.getInstance("SHA1PRNG");

    this.nbIterations = nbIterations;
  }

  @Override
  public IHashHolder hash(final String toHash, final IHashHolderDto hashData) {
    Pbkdf2HashHolder newHash;
    if (hashData == null) {
      newHash = new Pbkdf2HashHolder(nbIterations);
      newHash.setSalt(genSalt());
    } else {
      newHash = new Pbkdf2HashHolder(hashData);
    }

    final char[] chars = toHash.toCharArray();
    final PBEKeySpec spec =
        new PBEKeySpec(chars, newHash.getSalt(), newHash.getNbIterations(), HASH_SIZE);

    try {
      newHash.setHash(hashAlgorithm.generateSecret(spec).getEncoded());
    } catch (InvalidKeySpecException e) {
      throw new FatalException("Error while hashing a string", e);
    }

    return newHash;
  }

  @Override
  public IHashHolder unserializeCustomData(String data) {
    return new Pbkdf2HashHolder(Integer.parseInt(data));
  }

  @Override
  public boolean isHashOutdated(IHashHolderDto hash) {
    if (!(hash instanceof Pbkdf2HashHolder)) {
      throw new IllegalArgumentException("Hash holder does not match the algorithm's holder type");
    }

    return ((Pbkdf2HashHolder) hash).getNbIterations() < nbIterations;
  }

  private byte[] genSalt() {
    byte[] salt = new byte[SALT_SIZE];
    saltGenerator.nextBytes(salt);

    return salt;
  }
}
