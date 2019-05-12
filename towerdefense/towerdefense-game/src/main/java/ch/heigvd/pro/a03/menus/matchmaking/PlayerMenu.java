package ch.heigvd.pro.a03.menus.matchmaking;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.Executable;
import ch.heigvd.pro.a03.commands.gameclient.GameClientCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.GameScene;
import ch.heigvd.pro.a03.scenes.MatchMakingScene;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class PlayerMenu extends Menu {

    private User user;
    private Label usernameLabel;
    private TextButton readyButton;
    private TextButton cancelButton;

    public PlayerMenu(User user, MatchMakingScene scene, GameClient gameClient, Skin skin) {

        this.user = user;

        usernameLabel = new Label("", skin);
        usernameLabel.setAlignment(Align.center);

        readyButton = new TextButton("Ready!", skin);
        readyButton.addListener(new ButtonCommand(new GameClientCommand(gameClient) {
            @Override
            public void execute(Object... args) {
                getReceiver().ready(new Command<MatchMakingScene>(scene) {
                    @Override
                    public void execute(Object... args) {
                        readyButton.setVisible(false);
                        getReceiver().startGame();
                    }
                });
            }
        }));

        cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ButtonCommand(new GameClientCommand(gameClient) {
            @Override
            public void execute(Object... args) {
                getReceiver().quit();
            }
        }));

        updateMenu(false);
    }

    public void updateMenu(boolean withReadyButton) {

        getMenu().clear();

        usernameLabel.setText(user.getUsername());

        getMenu().defaults().prefWidth(UI.BUTTON_WIDTH).spaceBottom(UI.SPACING);
        getMenu().add(usernameLabel).growX();

        if (withReadyButton) {
            getMenu().row();
            getMenu().add(readyButton).prefHeight(UI.BUTTON_HEIGHT);
        }

        //getMenu().row();
        //getMenu().add(cancelButton).prefHeight(UI.BUTTON_HEIGHT);
    }
}
