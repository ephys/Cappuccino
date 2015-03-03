package paoo.cappuccino.util.hasher.pbkdf2;

import paoo.cappuccino.util.hasher.BaseHashHolder;
import paoo.cappuccino.util.hasher.IHashHolderDto;

/**
 * Holder class for pbkdf2 hashes
 *
 * @author Guylian Cox
 */
final class Pbkdf2HashHolder extends BaseHashHolder {

  private final int nbIterations;

  /**
   * Constructor~
   *
   * @param nbIterations the number of iterations PBKDF2 did when hashing.
   */
  public Pbkdf2HashHolder(int nbIterations) {
    this.nbIterations = nbIterations;
  }

  /**
   * Clones an already existing hash data holder.
   *
   * @param hashData the holder to clone.
   */
  public Pbkdf2HashHolder(IHashHolderDto hashData) {
    super(hashData);

    if (hashData instanceof Pbkdf2HashHolder) {
      this.nbIterations = ((Pbkdf2HashHolder) hashData).nbIterations;
    } else {
      throw new IllegalArgumentException("hashData must be an instance of Pbkdf2Hash.");
    }
  }

  @Override
  public String serializeCustomData() {
    return Integer.toString(nbIterations);
  }

  /**
   * Gets the number of iterations PBKDF2 did for this hash.
   */
  public int getNbIterations() {
    return nbIterations;
  }
}
