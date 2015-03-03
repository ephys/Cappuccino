package paoo.cappuccino.util.hasher.pbkdf2;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import paoo.cappuccino.util.exception.FatalException;
import paoo.cappuccino.util.hasher.IHashAlgorithm;
import paoo.cappuccino.util.hasher.IHashHolder;
import paoo.cappuccino.util.hasher.StringHasher;

/**
 * Hash algorithm based on PBKDF2
 *
 * @author Guylian Cox
 */
public final class Pbkdf2Hasher implements IHashAlgorithm {

  public static final int HASH_SIZE = 8 * 64;
  private final int nbIterations;

  private SecretKeyFactory hashAlgorithm;

  {
    try {
      hashAlgorithm = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      throw new FatalException("Could not fetch the hashing algorithm", e);
    }
  }

  /**
   * Constructor~
   *
   * @param nbIterations the number of iterations PBKDF2 should do.
   */
  public Pbkdf2Hasher(int nbIterations) {
    this.nbIterations = nbIterations;
  }

  @Override
  public IHashHolder hash(final String toHash, final IHashHolder hashData) {
    Pbkdf2HashHolder newHash;
    if (hashData == null) {
      newHash = new Pbkdf2HashHolder(nbIterations);
      newHash.setSalt(StringHasher.INSTANCE.genSalt());
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
}
