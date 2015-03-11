package paoo.cappuccino.util.hasher;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import paoo.cappuccino.Main;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * String hasher Unit test
 *
 * @author Guylian Cox
 */
public class TestStringHasher {

  private static DependencyInjector injector;

  @Inject
  private IStringHasher hasher;

  private char[] password = new char[]{'p', 'o', 'm', 'm', 'e'};
  private IHashHolderDto hashA;

  @BeforeClass
  public static void init() {
    injector = Main.configureApp(new AppContext("HasherTest", "0.0.1", "test"));
  }

  @Before
  public void inject() {
    injector.populate(this);

    hashA = hasher.hash(password);
  }

  @Test
  public void testHashSize() {
    assertEquals(hashA.getHash().length * 8, Pbkdf2Hasher.HASH_SIZE);
    assertEquals(hashA.getSalt().length, Pbkdf2Hasher.SALT_SIZE);
  }

  @Test
  public void testHashes() {
    IHashHolderDto hashB = hasher.hash(password);

    assertFalse("Re-hashing the same password should output a different hash.",
                Arrays.equals(hashB.getHash(), hashA.getHash()));
  }

  @Test
  public void testHashValidation() {
    assertTrue(hasher.matchHash(password, hashA));
  }

  @Test
  public void testRehashNoChanges() {
    assertFalse(hasher.isHashOutdated(hashA));
  }

  @Test
  public void testSerialize() {
    String serialization = hasher.serialize(hashA);
    IHashHolderDto postSer = hasher.unserialize(serialization);

    assertArrayEquals(hashA.getHash(), postSer.getHash());
    assertArrayEquals(hashA.getSalt(), postSer.getSalt());
    assertEquals(hashA.getAlgorithmVersion(), postSer.getAlgorithmVersion());
  }
}
