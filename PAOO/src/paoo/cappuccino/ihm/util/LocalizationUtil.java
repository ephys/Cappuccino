package paoo.cappuccino.ihm.util;

import paoo.cappuccino.business.dto.IParticipationDto;

public final class LocalizationUtil {
  private final static String[] stateLocalizations = {
      "Invité", "Confirmé", "Decliné", "Facturé", "Payé"
  };

  public static String localizeState(IParticipationDto.State state) {
    return stateLocalizations[state.ordinal()];
  }
}
