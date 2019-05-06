package ch.heigvd.pro.a03.menus.auth;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.auth.RegisterCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RegistrationMenu extends Menu {

    private AuthMenu parent;
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;

    public RegistrationMenu(AuthMenu authMenu, Skin skin) {

        this.parent = authMenu;

        Label usernameLabel = new Label("Username", skin);
        usernameField = new TextField("", skin);

        Label passwordLabel = new Label("Password", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        Label confirmPasswordLabel = new Label("Confirm password", skin);
        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');

        TextButton registrationButton = new TextButton("Register", skin);
        registrationButton.addListener(new ButtonCommand(new RegisterCommand(this)));

        // Add actors in table
        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.TEXT_FIELD_HEIGHT).left();
        getMenu().add(usernameLabel).expandX().left();
        getMenu().row();
        getMenu().add(usernameField);
        getMenu().row();
        getMenu().add(passwordLabel).expandX();
        getMenu().row();
        getMenu().add(passwordField);
        getMenu().row();
        getMenu().add(confirmPasswordLabel).expandX();
        getMenu().row();
        getMenu().add(confirmPasswordField).spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(registrationButton).prefHeight(UI.BUTTON_HEIGHT);
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
