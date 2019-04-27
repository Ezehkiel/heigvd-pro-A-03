package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.AuthCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AuthSelectionMenu extends Menu {

    public AuthSelectionMenu(AuthMenu authMenu, Skin skin) {

        TextButton connectionButton = new TextButton("Log in", skin);
        connectionButton.addListener(new ButtonCommand(new AuthCommand(authMenu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showConnectionMenu();
            }
        }));

        TextButton registrationButton = new TextButton("Register", skin);
        registrationButton.addListener(new ButtonCommand(new AuthCommand(authMenu) {
            @Override
            public void execute(Object... args) {
                getReceiver().showRegistrationMenu();
            }
        }));

        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
        getMenu().add(connectionButton).spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(registrationButton);
    }
}
