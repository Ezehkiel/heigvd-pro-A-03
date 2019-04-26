package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.menus.auth.AuthMenu;

public abstract class AuthCommand extends Command<AuthMenu> {

    public AuthCommand(AuthMenu receiver) {
        super(receiver);
    }
}
