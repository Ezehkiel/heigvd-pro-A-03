package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AuthSelectionMenu extends Menu {

    private TextButton connectionButton;
    private TextButton registrationButton;
    private TextButton cancelButton;
    private TextButton logoutButton;

    private RegistrationMenu registrationMenu;
    private ConnectionMenu connectionMenu;

    private boolean inSelection = false;
    private Skin skin;

    public AuthSelectionMenu(Skin skin) {

        this.skin = skin;
        getMenu().setDebug(true);

        connectionButton = new TextButton("Log in", skin);
        connectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                showConnectionMenu();
            }
        });

        registrationButton = new TextButton("Register", skin);
        registrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                showRegistrationMenu();
            }
        });

        cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                showAuthSelectionMenu();
            }
        });

        logoutButton = new TextButton("Log out", skin);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameLauncher.getInstance().setConnectedPlayer(null);
                showAuthSelectionMenu();
            }
        });

        connectionMenu = new ConnectionMenu(this, skin);
        registrationMenu = new RegistrationMenu(this, skin);

        showAuthSelectionMenu();
    }

    private void showConnectionMenu() {

        if (!inSelection) {
            return;
        }

        inSelection = false;

        getMenu().clear();

        getMenu().add(connectionMenu.getMenu()).spaceBottom(50);
        getMenu().row();
        addCancelButton();

    }

    private void showRegistrationMenu() {

        if (!inSelection) {
            return;
        }

        inSelection = false;

        getMenu().clear();

        getMenu().add(registrationMenu.getMenu()).spaceBottom(50);
        getMenu().row();
        addCancelButton();
    }

    private void showAuthSelectionMenu() {

        if (inSelection) {
            return;
        }

        inSelection = true;

        getMenu().clear();

        getMenu().add(connectionButton).prefWidth(250).prefHeight(50).spaceBottom(50);
        getMenu().row();
        getMenu().add(registrationButton).prefWidth(250).prefHeight(50);

    }

    public void showConnectedPlayerMenu() {

        inSelection = false;

        Player player = GameLauncher.getInstance().getConnectedPlayer();
        if (player == null) {
            return;
        }

        getMenu().clear();
        getMenu().add(new ConnectedPlayerMenu(player, skin).getMenu()).expandY();
        getMenu().row();
        getMenu().add(logoutButton).prefWidth(250).prefHeight(50);
    }

    private void addCancelButton() {

        getMenu().add(cancelButton).prefWidth(250).prefHeight(50);
    }
}
