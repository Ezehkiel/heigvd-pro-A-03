package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.Player;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

                System.out.println("Register: " + usernameField.getText() + " " + passwordField.getText());

                if (!passwordField.getText().equals(confirmPasswordField.getText())) {

                    System.out.println("Passwords do not match!");
                    return;
                }

                String json = "{\"username\": \""  + usernameField.getText() + "\"," +
                        "\"password\": \"" + passwordField.getText() + "\"}";

                try {

                    URL obj = new URL("http://ezehkiel.ch:3945/users/register");
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    connection.setRequestMethod("POST");

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

                        Player player = new Gson().fromJson(responseBuilder.toString(), Player.class);
                        GameLauncher.getInstance().setConnectedPlayer(player);

                        System.out.println(GameLauncher.getInstance().getConnectedPlayer().getUsername() + " successfully connected!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
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
