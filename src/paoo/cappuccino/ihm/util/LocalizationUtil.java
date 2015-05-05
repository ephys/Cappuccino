package paoo.cappuccino.ihm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;

/**
 * Utility methods to localize objects.
 */
public final class LocalizationUtil {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
  private static final String[] stateLocalizations = {
      "Invité", "Confirmé", "Decliné", "Facturé", "Payé"
  };

  public static String localizeState(IParticipationDto.State state) {
    return stateLocalizations[state.ordinal()];
  }

  public static String localizeDate(LocalDateTime date) {
    return date.format(formatter);
  }

  public static String localizeAddress(ICompanyDto company) {
    return company.getAddressNum() + " " + company.getAddressStreet() + ", "
           + company.getAddressPostcode() + " " + company.getAddressTown();
  }
}
