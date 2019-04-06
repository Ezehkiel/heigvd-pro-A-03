package ch.heigvd.pro.a03.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.common.hash.Hashing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

                try {

                    URL obj = new URL("http://ezehkiel.ch:3945/users/login");
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("POST");

                    String json = "{\"username\": \""  + usernameField.getText() + "\", \"password\": \"" + passwordField.getText() + "\"}";

                    // For POST only - START
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(json.getBytes());
                    os.flush();
                    os.close();
                    // For POST only - END

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                        StringBuilder responseBuilder = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }

                        System.out.println(responseBuilder);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        // Add actors in table
        getMenu().defaults().prefWidth(250).prefHeight(25).left();
        getMenu().add(usernameLabel).expandX();
        getMenu().row();
        getMenu().add(usernameField);
        getMenu().row();
        getMenu().add(passwordLabel).expandX();
        getMenu().row();
        getMenu().add(passwordField).spaceBottom(50);
        getMenu().row();
        getMenu().add(loginButton).prefHeight(50);
    }
}
