package paoo.cappuccino.ihm.companyselection;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.ihm.core.Initializable;
import paoo.cappuccino.ihm.home.HomeModel;

/**
 * Model for the company selection Gui.
 *
 * @author Maduka Junior
 */
public class CompanySelectionModel extends HomeModel implements Initializable {

  @Override
  public void init(Object[] data) {
    if (data == null || data.length == 0) {
      return;
    }

    if (!(data[0] instanceof IBusinessDayDto)) {
      throw new IllegalArgumentException("Incorrect initialization data for CompanySelectionModel");
    }

    setSelectedDay((IBusinessDayDto) data[0]);
  }
}
