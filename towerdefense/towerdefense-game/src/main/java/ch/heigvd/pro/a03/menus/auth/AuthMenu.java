package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.AuthCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class AuthMenu extends Menu {

    private TextButton cancelButton;

    private AuthSelectionMenu authSelectionMenu;
    private RegistrationMenu registrationMenu;
    private ConnectionMenu connectionMenu;

    private Skin skin;

    public AuthMenu(Skin skin) {

        this.skin = skin;

        getMenu().defaults();

        cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ButtonCommand(new AuthCommand(this) {
            @Override
            public void execute(Object... args) {
                showAuthSelectionMenu();
            }
        }));

        authSelectionMenu = new AuthSelectionMenu(this, skin);
        connectionMenu = new ConnectionMenu(this, skin);
        registrationMenu = new RegistrationMenu(this, skin);

        showAuthSelectionMenu();
    }

    public void showConnectionMenu() {

        getMenu().clear();

        getMenu().add(connectionMenu.getMenu()).spaceBottom(UI.SPACING);
        addCancelButton();
    }

    public void showRegistrationMenu() {

        getMenu().clear();

        getMenu().add(registrationMenu.getMenu()).spaceBottom(UI.SPACING);
        addCancelButton();
    }

    public void showAuthSelectionMenu() {

        getMenu().clear();

        getMenu().add(authSelectionMenu.getMenu());
    }

    public void showConnectedPlayerMenu() {

        User player = GameLauncher.getInstance().getConnectedPlayer();
        if (player == null) {
            return;
        }

        getMenu().clear();

        getMenu().add(new ConnectedPlayerMenu(player, this, skin).getMenu()).expandY();
    }

    private void addCancelButton() {
        getMenu().row();
        getMenu().add(cancelButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
    }
}