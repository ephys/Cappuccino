package paoo.cappuccino.util.hasher;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import paoo.cappuccino.Main;
import paoo.cappuccino.core.AppContext;
import paoo.cappuccino.core.injector.DependencyInjector;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.util.hasher.pbkdf2.Pbkdf2Hasher;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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

  @BeforeClass
  public static void init() {
    injector = Main.configureApp(new AppContext("HasherTest", "0.0.1", "test"));
  }

  @Before
  public void inject() {
    injector.populate(this);
  }

  @Test
  public void testHashSize() {
    IHashHolderDto hashA = hasher.hash("pomme");
    assertEquals(hashA.getHash().length * 8, Pbkdf2Hasher.HASH_SIZE);
    assertEquals(hashA.getSalt().length, Pbkdf2Hasher.SALT_SIZE);
  }

  @Test
  public void testHashes() {
    IHashHolderDto hashA = hasher.hash("pomme");
    IHashHolderDto hashB = hasher.hash("pomme");

    assertThat(hashA.getHash(), not(equalTo(hashB.getHash())));
  }

  @Test
  public void testHashValidation() {
    IHashHolderDto hashA = hasher.hash("pomme");
    assertTrue(hasher.matchHash("pomme", hashA));
  }

  @Test
  public void testRehashNoChanges() {
    IHashHolderDto hashA = hasher.hash("pomme");
    assertFalse(hasher.isHashOutdated(hashA));
  }

  @Test
  public void testSerialize() {
    IHashHolderDto preSer = hasher.hash("pomme");
    String serialization = hasher.serialize(preSer);
    IHashHolderDto postSer = hasher.unserialize(serialization);

    assertArrayEquals(preSer.getHash(), postSer.getHash());
    assertArrayEquals(preSer.getSalt(), postSer.getSalt());
    assertEquals(preSer.getAlgorithmVersion(), postSer.getAlgorithmVersion());
  }
}
