package ch.heigvd.pro.a03.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AuthSelectionMenu extends Menu {

    private TextButton connectionButton;
    private TextButton registrationButton;
    private TextButton cancelButton;

    private RegistrationMenu registrationMenu;
    private ConnectionMenu connectionMenu;

    private boolean inSelection = false;

    public AuthSelectionMenu(Skin skin) {

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

        connectionMenu = new ConnectionMenu(skin);
        registrationMenu = new RegistrationMenu(skin);

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

    private void addCancelButton() {

        getMenu().add(cancelButton).prefWidth(250).prefHeight(50);
    }
}
