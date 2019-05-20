package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.auth.AuthMenu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.server.HttpServerUtils;

/**
 * Commands for a Auth menu
 */
public abstract class AuthCommand extends Command<AuthMenu> {

    public AuthCommand(AuthMenu receiver) {
        super(receiver);
    }

    /**
     * Check if the player can be connected.
     * @param player user
     */
    public void checkPlayer(User player) {

        if (player == null) {
            getReceiver().showError(HttpServerUtils.getErrorMessage());
            return;
        }

        GameLauncher.getInstance().setConnectedPlayer(player);
        getReceiver().showConnectedPlayerMenu();
    }
}
