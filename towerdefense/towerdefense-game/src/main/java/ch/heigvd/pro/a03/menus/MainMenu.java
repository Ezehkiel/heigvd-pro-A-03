package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.GameModeScene;
import ch.heigvd.pro.a03.scenes.MatchMakingScene;
import ch.heigvd.pro.a03.scenes.SettingsScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu extends Menu {

    public MainMenu(Skin skin) {
        super();

        // Buttons
        TextButton onlineButton = new TextButton("Play Online", skin, "default");
        onlineButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public boolean execute(Object... args) {

                if (getReceiver().getConnectedPlayer() != null) {
                    getReceiver().getSceneManager().add(new MatchMakingScene());
                    return true;
                }

                return false;
            }
        }));

        TextButton offlineButton = new TextButton("Play Offline", skin, "default");
        offlineButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public boolean execute(Object... args) {

                GameLauncher.getInstance().getSceneManager().add(new GameModeScene());
                return true;
            }
        }));

        TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public boolean execute(Object... args) {

                getReceiver().getSceneManager().add(new SettingsScene());
                return true;
            }
        }));

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public boolean execute(Object... args) {
                getReceiver().exit();
                return true;
            }
        }));

        // Add actors in menu
        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT).spaceBottom(UI.SPACING);
        getMenu().add(onlineButton);
        getMenu().row();
        getMenu().add(offlineButton);
        getMenu().row();
        getMenu().add(settingsButton);
        getMenu().row();
        getMenu().add(exitButton);
    }
}
