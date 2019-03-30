package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.scenes.GameModeScene;
import ch.heigvd.pro.a03.scenes.SettingsScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu extends Menu {

    public MainMenu(Skin skin) {
        super();

        // Buttons
        TextButton onlineButton = new TextButton("Play Online", skin, "default");
        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameLauncher.getInstance().getSceneManager().add(new GameModeScene());
            }
        });

        TextButton offlineButton = new TextButton("Play Offline", skin, "default");
        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameLauncher.getInstance().getSceneManager().add(new GameModeScene());
            }
        });

        TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameLauncher.getInstance().getSceneManager().add(new SettingsScene());
            }
        });

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        // Add actors in menu
        getMenu().defaults().prefWidth(250).prefHeight(50).spaceBottom(50);
        getMenu().add(onlineButton);
        getMenu().row();
        getMenu().add(offlineButton);
        getMenu().row();
        getMenu().add(settingsButton);
        getMenu().row();
        getMenu().add(exitButton);
    }
}
