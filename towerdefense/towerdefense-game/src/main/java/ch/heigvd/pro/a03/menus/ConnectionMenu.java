package ch.heigvd.pro.a03.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ConnectionMenu extends Menu {

    private TextField usernameField;
    private TextField passwordField;

    public ConnectionMenu(Skin skin) {
        super();

        Label usernameLabel = new Label("Username", skin);
        usernameField = new TextField("", skin);

        Label passwordLabel = new Label("Password", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        TextButton loginButton = new TextButton("Log in", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println("Connect: " + usernameField.getText() + " " + passwordField.getText());
            }
        });

        // Add actors in table
        getMenu().add(usernameLabel).expandX().left().prefHeight(50);
        getMenu().row();
        getMenu().add(usernameField).prefWidth(250).prefHeight(50).left().bottom();
        getMenu().row();
        getMenu().add(passwordLabel).expandX().prefHeight(50).left().bottom();
        getMenu().row();
        getMenu().add(passwordField).prefWidth(250).prefHeight(50).spaceBottom(50);
        getMenu().row();
        getMenu().add(loginButton).prefWidth(250).prefHeight(50);
    }
}
