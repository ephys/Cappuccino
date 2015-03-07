package paoo.cappuccino.business.dto;

/**
 * Interface containing getter methods shared by every DTO.
 *
 * @author Nicolas Fischer
 */
public interface IBaseDto {

  /**
   * Gets the entity identifier.
   */
  int getId();

  /**
   * Gets the entity version.
   */
  int getVersion();
}
