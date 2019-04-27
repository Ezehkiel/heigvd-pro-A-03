package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.LoginCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ConnectionMenu extends Menu {

    private AuthMenu parent;
    private TextField usernameField;
    private TextField passwordField;

    public ConnectionMenu(AuthMenu authMenu, Skin skin) {

        this.parent = authMenu;

        Label usernameLabel = new Label("Username", skin);
        usernameField = new TextField("", skin);

        Label passwordLabel = new Label("Password", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextButton loginButton = new TextButton("Log in", skin);
        loginButton.addListener(new ButtonCommand(new LoginCommand(this)));

        // Add actors in table
        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.TEXT_FIELD_HEIGHT).left();
        getMenu().add(usernameLabel).expandX();
        getMenu().row();
        getMenu().add(usernameField);
        getMenu().row();
        getMenu().add(passwordLabel).expandX();
        getMenu().row();
        getMenu().add(passwordField).spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(loginButton).prefHeight(UI.BUTTON_HEIGHT);
    }

    public AuthMenu getParent() {
        return parent;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
