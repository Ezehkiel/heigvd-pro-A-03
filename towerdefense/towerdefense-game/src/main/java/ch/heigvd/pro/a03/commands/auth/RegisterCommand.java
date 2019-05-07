package ch.heigvd.pro.a03.commands.auth;

import ch.heigvd.pro.a03.menus.auth.AuthMenu;
import ch.heigvd.pro.a03.menus.auth.RegistrationMenu;
import ch.heigvd.pro.a03.utils.HttpServerUtils;

public class RegisterCommand extends AuthCommand {

    private RegistrationMenu menu;

    public RegisterCommand(RegistrationMenu menu, AuthMenu receiver) {
        super(receiver);
        this.menu = menu;
    }

    @Override
    public void execute(Object... args) {

        getReceiver().clearError();

        if (!menu.passwordsMatch()) {
            getReceiver().showError("Passwords do not match.");

        } else if (menu.getPassword().length() <= 0) {
            getReceiver().showError("Please, enter a valid password.");

        } else if (menu.getUsername().length() <= 0) {
            getReceiver().showError("Please, enter a valid username.");

        } else {
            checkPlayer(HttpServerUtils.register(menu.getUsername(), menu.getPassword()));
        }
    }
}
