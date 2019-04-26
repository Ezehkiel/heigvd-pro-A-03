package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.menus.auth.AuthMenu;

public class LogoutCommand extends AuthCommand {

    public LogoutCommand(AuthMenu receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(Object... args) {

        GameLauncher.getInstance().setConnectedPlayer(null);
        getReceiver().showAuthSelectionMenu();

        return true;
    }
}
