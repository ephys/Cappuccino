package paoo.cappuccino.ihm.registration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.core.injector.NoCache;
import paoo.cappuccino.ihm.core.IGuiManager;
import paoo.cappuccino.ihm.util.BaseFrame;
import paoo.cappuccino.ucc.IUserUcc;

/**
 * Frame for the registration Gui.
 *
 * @author Maduka Junior
 */
@NoCache
public class RegistrationFrame extends BaseFrame {

  /**
   * Creates a new frame for the registration gui.
   *
   * @param userUcc User use case controller.
   * @param guiManager The manager responsible for opening/closing this frame.
   */
  @Inject
  public RegistrationFrame(IUserUcc userUcc, IGuiManager guiManager) {
    super("S'inscrire", 500, 660, guiManager);
    this.setMinimumSize(new Dimension(500, 660));

    this.getContentPane().setBackground(new Color(212, 82, 82));

    this.setLayout(new GridBagLayout());
    this.add(new RegistrationViewController(new RegistrationModel(), guiManager, userUcc),
             new GridBagConstraints());

    this.setVisible(true);
  }
}
