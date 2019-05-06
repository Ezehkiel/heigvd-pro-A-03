package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.auth.RegistrationMenu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.HttpServerUtils;

public class RegisterCommand extends Command<RegistrationMenu> {

    public RegisterCommand(RegistrationMenu registrationMenu) {
        super(registrationMenu);
    }

    @Override
    public void execute(Object... args) {

        User player = HttpServerUtils.register(getReceiver().getUsername(), getReceiver().getPassword());

        if (player == null) {
            return;
        }

        GameLauncher.getInstance().setConnectedPlayer(player);
        getReceiver().getParent().showConnectedPlayerMenu();
    }
}
