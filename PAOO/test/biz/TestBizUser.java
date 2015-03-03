package biz;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IUserDto;
import paoo.cappuccino.business.entity.IUser;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.config.AppContext;
import paoo.cappuccino.config.injector.DependencyInjector;
import paoo.cappuccino.config.injector.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Classe de test junit pour le bizuser
 */
public class TestBizUser {

  private
  @Inject
  IEntityFactory entityFactory;

  private int id;
  private String username;
  private String password;
  private String lastName;
  private String firstName;
  private String email;
  private LocalDateTime registerDate;
  private IUserDto.Role role;
  private IUser user1;
  private IUser user2;

  /**
   * Methode lancee 1 seule fois au chargement de la classe
   */
  @BeforeClass
  public static void setupBeforeClass() {
    AppContext.INSTANCE.setup("UserEntityTest", "0.0.1", "test");
  }

  /**
   * Initialisation des attributs pour les tests
   */

  @Before
  public void setAttributs() throws Exception {
    DependencyInjector.populate(this);

    this.id = 1;
    this.username = "Nicolas";
    this.password = "pomme";
    this.lastName = "Fischer";
    this.firstName = "Benoit";
    this.email = "nicolas@gmail.com";
    this.registerDate = LocalDateTime.now();
    this.role = IUserDto.Role.USER;

    user1 = entityFactory.createUser(username, password, lastName, firstName, email,
                                     role);
  }

  /**
   * verifie que la date d'inscription en attribut est la meme que la date d'inscription renvoye par
   * getter
   */
  @Test
  public void testRegister_date() {
    assertEquals(this.registerDate, this.user1.getRegisterDate());
  }

  /**
   * verifie que l'id de l'utilisateur en attribut est la meme que l'id du l'utilisateur renvoye par
   * getter
   */
  @Test
  public void testGetIdUuser() {
    assertEquals(this.id, this.user1.getId());
  }

  /**
   * verifie que l'email en attribut est la meme que l'email du getter
   */
  @Test
  public void testEmail() {
    assertEquals(this.email, this.user1.getEmail());
  }

  /**
   * verifie que le nom en attribut est la meme que le nom de l'utilisateur renvoye par getter
   */
  @Test
  public void testLastName() {
    assertEquals(this.lastName, this.user1.getLastName());
  }

  /**
   * verifie que le prenom en attribut est la meme que le prenom de l'utilisateur renvoye par
   * getter
   */
  @Test
  public void testFirstName() {
    assertEquals(this.firstName, this.user1.getFirstName());
  }

  /**
   * verifie que le mot de passe est la meme que le mot de passe de l'utilisateur renvoye par
   * getter
   */
  @Test
  public void testPassword() {
    assertEquals(this.password, this.user1.getPassword());
  }

  /**
   * verifie que le role en attribut est la meme que le role de l'utilisateur renvoye par getter
   */
  @Test
  public void testRole() {
    assertEquals(this.role, this.user1.getRole());
  }

  /**
   * verifie que le pseudo en attribut est la meme que le pseudo de l'utilisateur renvoye par
   * getter
   */
  @Test
  public void testUsername() {
    assertEquals(this.username, this.user1.getUsername());
  }


  /**
   * Test la valeur de retour de la methode authentifier
   */
  @Test
  public void testAuthentifierVrai() {
    assertTrue(this.user1.isPassword(this.password));
  }

  /**
   * Test la valeur de retour de la methode authentifier
   */
  @Test
  public void testAuthentifierFaux() {
    assertFalse(this.user1.isPassword("mcp"));
  }
}
