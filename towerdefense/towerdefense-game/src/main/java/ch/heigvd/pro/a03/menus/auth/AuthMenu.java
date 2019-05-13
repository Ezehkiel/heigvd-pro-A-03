package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.AuthCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class AuthMenu extends Menu {

    private TextButton cancelButton;

    private Label errorLabel;
    private AuthSelectionMenu authSelectionMenu;
    private RegistrationMenu registrationMenu;
    private ConnectionMenu connectionMenu;

    private Skin skin;

    public AuthMenu(Skin skin) {

        this.skin = skin;

        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);

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

        addErrorLabel();

        getMenu().add(connectionMenu.getMenu()).spaceBottom(UI.SPACING);
        addCancelButton();
    }

    public void showRegistrationMenu() {

        getMenu().clear();

        addErrorLabel();

        getMenu().add(registrationMenu.getMenu()).spaceBottom(UI.SPACING);
        addCancelButton();
    }

    public void showAuthSelectionMenu() {

        getMenu().clear();

        addErrorLabel();

        getMenu().add(authSelectionMenu.getMenu());
    }

    public void showConnectedPlayerMenu() {

        User player = GameLauncher.getInstance().getConnectedPlayer();
        if (player == null) {
            return;
        }

        getMenu().clear();

        addErrorLabel();

        getMenu().add(new ConnectedPlayerMenu(player, this, skin).getMenu()).expandY();
    }

    private void addCancelButton() {
        getMenu().row();
        getMenu().add(cancelButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
    }

    private void addErrorLabel() {

        clearError();
        getMenu().add(errorLabel);
        getMenu().row();
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    public void clearError() {
        errorLabel.setText("");
    }
}
