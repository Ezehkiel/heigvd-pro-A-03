package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.auth.ConnectionMenu;
import ch.heigvd.pro.a03.utils.HttpServerUtils;

public class LoginCommand extends Command<ConnectionMenu> {

    public LoginCommand(ConnectionMenu receiver) {
        super(receiver);
    }

    @Override
    public void execute(Object... args) {

        Player player = HttpServerUtils.login(getReceiver().getUsername(), getReceiver().getPassword());

        if (player == null) {
            return;
        }

        GameLauncher.getInstance().setConnectedPlayer(player);
        getReceiver().getParent().showConnectedPlayerMenu();
    }
}
