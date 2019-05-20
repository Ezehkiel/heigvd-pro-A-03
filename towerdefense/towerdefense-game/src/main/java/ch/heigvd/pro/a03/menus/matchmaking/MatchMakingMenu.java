package ch.heigvd.pro.a03.menus.matchmaking;

import ch.heigvd.pro.a03.commands.ButtonCommand;
import ch.heigvd.pro.a03.commands.Command;
import ch.heigvd.pro.a03.commands.gameclient.GameClientCommand;
import ch.heigvd.pro.a03.menus.Menu;
import ch.heigvd.pro.a03.scenes.MatchMakingScene;
import ch.heigvd.pro.a03.server.GameClient;
import ch.heigvd.pro.a03.users.User;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MatchMakingMenu extends Menu {

    Label title;
    TextButton readyButton;

    public MatchMakingMenu(User user, GameClient gameClient, MatchMakingScene scene, Skin skin) {

        getMenu().setFillParent(true);

        title = new Label("Connecting to game", skin, "title");

        readyButton = new TextButton("YES", skin);
        hideReadyButton();
        readyButton.addListener(new ButtonCommand(new GameClientCommand(gameClient) {
            @Override
            public void execute(Object... args) {
                getReceiver().ready(new Command<MatchMakingScene>(scene) {
                    @Override
                    public void execute(Object... args) {
                        getReceiver().startGame();
                    }
                });
            }
        }));
        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ButtonCommand(args -> scene.quit()));

        getMenu().add(title).spaceBottom(UI.SPACING);
        getMenu().row();
        getMenu().add(readyButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
        getMenu().row();
        getMenu().add(quitButton).prefWidth(UI.BUTTON_WIDTH).prefHeight(UI.BUTTON_HEIGHT);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void showReadyButton() {
        readyButton.setVisible(true);
    }

    public void hideReadyButton() {
        readyButton.setVisible(false);
    }
}
