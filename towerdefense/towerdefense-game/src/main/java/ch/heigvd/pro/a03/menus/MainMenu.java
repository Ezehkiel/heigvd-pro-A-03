package ch.heigvd.pro.a03.menus;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.scenes.GameModeScene;
import ch.heigvd.pro.a03.scenes.MatchMakingScene;
import ch.heigvd.pro.a03.scenes.ScoreBoardScene;
import ch.heigvd.pro.a03.scenes.SettingsScene;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu extends Menu {

    public MainMenu(Skin skin) {
        super();

        // Buttons
        TextButton onlineButton = new TextButton("Play online", skin, "default");
        onlineButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {

                if (getReceiver().getConnectedPlayer() != null) {
                    getReceiver().getSceneManager().add(new MatchMakingScene());
                }
            }
        }));

        TextButton offlineButton = new TextButton("Play offline", skin, "default");
        offlineButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {
                GameLauncher.getInstance().getSceneManager().add(new GameModeScene());
            }
        }));

        TextButton scoresButton = new TextButton("Score board", skin, "default");
        scoresButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {
                getReceiver().getSceneManager().add(new ScoreBoardScene());
            }
        }));

        TextButton settingsButton = new TextButton("Settings", skin, "default");
        settingsButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {
                getReceiver().getSceneManager().set(new SettingsScene());
            }
        }));

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.addListener(new ButtonCommand(new Command<GameLauncher>(GameLauncher.getInstance()) {
            @Override
            public void execute(Object... args) {
                getReceiver().exit();
            }
        }));

        // Add actors in menu
        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT).spaceBottom(UI.SPACING);
        getMenu().add(onlineButton);
        getMenu().row();
        getMenu().add(offlineButton);
        getMenu().row();
        getMenu().add(scoresButton);
        getMenu().row();
        getMenu().add(settingsButton);
        getMenu().row();
        getMenu().add(exitButton);
    }
}
