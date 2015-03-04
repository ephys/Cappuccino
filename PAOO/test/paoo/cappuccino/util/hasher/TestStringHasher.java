package paoo.cappuccino.util.hasher;

import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.config.AppContext;
import paoo.cappuccino.config.Config;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * String hasher Unit test
 *
 * @author Guylian Cox
 */
public class TestStringHasher {

  @BeforeClass
  public static void init() {
    AppContext.INSTANCE.setup("HasherTest", "0.0.1", "test");
    StringHasher.INSTANCE.addHashAlgorithm(new Pbkdf2Hasher(Config.getInt("pbkdf2_iterations")));
  }

  @Test
  public void testHashSize() {
    IHashHolderDto hashA = StringHasher.INSTANCE.hash("pomme");
    assertEquals(hashA.getHash().length * 8, Pbkdf2Hasher.HASH_SIZE);
    assertEquals(hashA.getSalt().length, StringHasher.SALT_SIZE);
  }

  @Test
  public void testHashes() {
    IHashHolderDto hashA = StringHasher.INSTANCE.hash("pomme");
    IHashHolderDto hashB = StringHasher.INSTANCE.hash("pomme");

    assertThat(hashA.getHash(), not(equalTo(hashB.getHash())));
  }

  @Test
  public void testHashValidation() {
    IHashHolderDto hashA = StringHasher.INSTANCE.hash("pomme");
    assertTrue(StringHasher.INSTANCE.matchHash("pomme", hashA));
  }

  @Test
  public void testRehashNoChanges() {
    IHashHolderDto hashA = StringHasher.INSTANCE.hash("pomme");
    IHashHolderDto hashB = StringHasher.INSTANCE.reHash("pomme", hashA);
    assertNull(hashB);
  }

  @Test
  public void testSerialize() {
    IHashHolderDto preSer = StringHasher.INSTANCE.hash("pomme");
    String serialization = StringHasher.INSTANCE.serialize(preSer);
    IHashHolderDto postSer = StringHasher.INSTANCE.unserialize(serialization);

    assertArrayEquals(preSer.getHash(), postSer.getHash());
    assertArrayEquals(preSer.getSalt(), postSer.getSalt());
    assertEquals(preSer.getAlgorithmVersion(), postSer.getAlgorithmVersion());
  }
}
