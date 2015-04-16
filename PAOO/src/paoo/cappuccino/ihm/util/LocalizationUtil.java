package paoo.cappuccino.ihm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import paoo.cappuccino.business.dto.IParticipationDto;

public final class LocalizationUtil {
  private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
  private final static String[] stateLocalizations = {
      "Invité", "Confirmé", "Decliné", "Facturé", "Payé"
  };

  public static String localizeState(IParticipationDto.State state) {
    return stateLocalizations[state.ordinal()];
  }

  public static String localizeDate(LocalDateTime date) {
    return date.format(formatter);
  }
}
