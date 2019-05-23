package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.auth.AuthMenu;

/**
 * Logs out a player
 */
public class LogoutCommand extends AuthCommand {

    /**
     * Creates a new player log out command.
     * @param receiver the receiver
     */
    public LogoutCommand(AuthMenu receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {

        GameLauncher.getInstance().setConnectedPlayer(null);
        getReceiver().showAuthSelectionMenu();
    }
}
