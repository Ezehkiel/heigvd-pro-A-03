package ch.heigvd.pro.a03.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RegistrationMenu extends Menu {

    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;

    public RegistrationMenu(Skin skin) {
        super();

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
        registrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println("Connect: " + usernameField.getText() + " " + passwordField.getText());
            }
        });

        // Add actors in table
        getMenu().defaults().prefWidth(250).prefHeight(25).left();
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
        getMenu().add(confirmPasswordField).spaceBottom(50);
        getMenu().row();
        getMenu().add(registrationButton).prefHeight(50);
    }
}
