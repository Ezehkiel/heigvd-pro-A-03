package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.menus.auth.AuthMenu;
import ch.heigvd.pro.a03.menus.auth.ConnectionMenu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.server.HttpServerUtils;

public class LoginCommand extends AuthCommand {

    private ConnectionMenu menu;

    public LoginCommand(ConnectionMenu menu, AuthMenu receiver) {
        super(receiver);
        this.menu = menu;
    }

    @Override
    public void execute(Object... args) {

        getReceiver().clearError();

        checkPlayer(HttpServerUtils.login(menu.getUsername(), menu.getPassword()));
    }
}
